package task;

import java.util.ArrayList;

public class TagText {
    private String textTag="";
    private String tagOpen;
    private String tagClose;
    private int count =0;
    private  TagText tagArray ;//список для вложений
    public TagText(String tag){
        tagOpen = "<"+tag;
        tagClose = "</"+tag+">";
        textTag+=tagOpen;
        count++;
    }

    public String getTagOpen() {
        return tagOpen;
    }
    public String getTagClose(){
        return tagClose;
    }
    public void setTextTag(String text){
        if(text.indexOf(tagOpen)==-1){

            textTag+=text+tagClose;
            count--;
            if(tagArray!=null&&tagArray.getCount()!=0){
                tagArray.setTextTag(text);
            }
        }
        else{
            textTag+=text;
            count++;
            tagArray = new TagText(tagOpen.substring(1,tagOpen.length()));
        }
    }
    public String getTextTag(){
        return textTag;
    }
    public int getCount(){
        return count;
    }

    public void printTag(){
        System.out.println(textTag);
        if(tagArray!=null){
            tagArray.printTag();
        }
    }
}
