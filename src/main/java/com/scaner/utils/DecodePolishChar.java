package com.scaner.utils;

import com.scaner.model.Advert;

public class DecodePolishChar {

    public static String decodeChar(String content) {

        String content1 = new String();
        String content2 = new String();

        String[] polishChar = {"ą", "ę", "ć", "ł", "ń", "ó", "ś", "ź", "ż", "m²", "Ą", "Ę", "Ć", "Ł", "Ń", "Ó", "Ś", "Ź", "Ż"};
        String[] latinChar = {"a", "e", "c", "l", "n", "o", "s", "z", "z", "m2", "A", "E", "C", "L", "N", "O", "S", "Z", "Z"};
        for (int i = 0; i < polishChar.length - 1; i++) {
            if(i==0){
                content2=content;
            }
            content1 = content2.replaceAll(polishChar[i], latinChar[i]);
            content2 = content1;
        }
        //System.out.println(content2);
        return content2;
    }
}
