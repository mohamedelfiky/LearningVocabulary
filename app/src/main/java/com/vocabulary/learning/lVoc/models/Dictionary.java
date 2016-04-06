package com.vocabulary.learning.lVoc.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mohamed on 26/03/16.
 */
@Table(name = "dictionary")
public class Dictionary extends Model {
    @Column(name = "ar")
    public String ar;

    @Column(name = "en")
    public String en;

    public List<String> answers= new ArrayList<>();

    public static List<Dictionary> getTwo() {
        return new Select().from(Dictionary.class).orderBy("RANDOM()").limit(2).execute();
    }

    public static Dictionary getQuestionWithAnswers(String excludes) {
        List<Dictionary> dics = new Select().from(Dictionary.class).orderBy("RANDOM()").where("Id NOT IN (?)", excludes).limit(4).execute();
        for (int i = 0; i < dics.size(); i++) {
            dics.get(0).answers.add(dics.get(i).en);
        }
        Collections.shuffle(dics.get(0).answers);
        return dics.get(0);
    }

    public static Dictionary getWord() {
        return new Select().from(Dictionary.class).orderBy("RANDOM()").executeSingle();
    }

    @Override
    public String toString() {
        return en + " : " + ar;
    }
}

