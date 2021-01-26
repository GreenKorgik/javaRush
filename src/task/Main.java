package task;

import java.io.*;
import java.util.ArrayList;
/*
Знакомство с тегами
Считай с консоли имя файла, который имеет HTML-формат.

Пример:
Info about Leela <span xml:lang="en" lang="en"><b><span>Turanga Leela
</span></b></span><span>Super</span><span>girl</span>

Первым параметром в метод main приходит тег. Например, "span".
Вывести на консоль все теги, которые соответствуют заданному тегу.
Каждый тег на новой строке, порядок должен соответствовать порядку следования в файле.
Количество пробелов, \n, \r не влияют на результат.
Файл не содержит тег CDATA, для всех открывающих тегов имеется отдельный закрывающий тег, одиночных тегов нет.
Тег может содержать вложенные теги.

Пример вывода:
<span xml:lang="en" lang="en"><b><span>Turanga Leela</span></b></span>
<span>Turanga Leela</span>
<span>Super</span>
<span>girl</span>

Шаблон тега:
<tag>text1</tag>
<tag text2>text1</tag>
<tag
text2>text1</tag>

text1, text2 могут быть пустыми


Требования:
1. Программа должна считывать имя файла с консоли (используй BufferedReader).
2. BufferedReader для считывания данных с консоли должен быть закрыт.
3. Программа должна считывать содержимое файла (используй FileReader).
4. Поток чтения из файла (FileReader) должен быть закрыт.
5. Программа должна выводить в консоль все теги, которые соответствуют тегу, заданному в параметре метода main.
*/

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String nameFile = reader.readLine();
        reader.close();
        BufferedReader readerFile = new BufferedReader(new FileReader(nameFile));
        String text ="";
        while(readerFile.ready()){
            text+=readerFile.readLine();
        }
        readerFile.close();
        ArrayList<TagText> listTagText = new ArrayList<>();//
        while(text.indexOf("<"+args[0])!=-1) {//проверяется есть ли вообще искомый открывающийся тег
                TagText tagText = new TagText(args[0]);//при создании обьекта мы уверены, открывающийся тег есть. Обратите внимание на конструктор
                text = text.substring(text.indexOf(tagText.getTagOpen()) + tagText.getTagOpen().length());// режем строку до конца открывающегося тега, концом считаем <tag
                while (tagText.getCount() != 0) {//если есть открывающийся тег, точно есть и закрывающийся по условию
                    if (text.indexOf(tagText.getTagOpen()) > text.indexOf(tagText.getTagClose())||text.indexOf("<"+args[0])==-1) { //смотрится, какой тег идет впереди открывающийся или закрывающийся(может быть множество вложений), второй возможный вариант это последний элемент списка тегов поэтому закрытие тега все еще есть, а открытый тег мы уже обрезали.
                        tagText.setTextTag(text.substring(0, text.indexOf(tagText.getTagClose())));//вложенности нет, следовательно мы бахаем строчку до закрывающегося тега(счетчик убывает на 1)
                        text = text.substring(text.indexOf(tagText.getTagClose()) + tagText.getTagClose().length());//и обрезаем строку до конца закрывающегося
                    } else {
                        tagText.setTextTag(text.substring(0, text.indexOf(tagText.getTagOpen()) + tagText.getTagOpen().length()));//вложенность, отправляем строчку до конца открывающегося тега увеличивая счетчик на 1
                        text = text.substring(text.indexOf(tagText.getTagOpen()) + tagText.getTagOpen().length());//и обрезаем соответственно до конца открывающегося
                    }
                }
                listTagText.add(tagText);
        }
        for(int i = 0;i < listTagText.size(); i++){
            listTagText.get(i).printTag();
        }

    }
}
