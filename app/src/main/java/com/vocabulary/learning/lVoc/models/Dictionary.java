package com.vocabulary.learning.lVoc.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.vocabulary.learning.lVoc.utils.LVoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * Created by mohamed on 26/03/16.
 */
@Table(name = "dictionary")
public class Dictionary extends Model {
    @Column(name = "ar")
    public String ar;

    @Column(name = "en")
    public String en;

    public List<String> answers = new ArrayList<>();

    public static List<Dictionary> findVocabulariesWithExcludes(String excludes, Integer limit) {
        return new Select()
                .from(Dictionary.class)
                .orderBy("RANDOM()")
                .where(String.format("Id NOT IN (%s)", excludes))
                .limit(limit)
                .execute();
    }


    /**
     * get list of vocabularies by ids
     *
     * @param ids
     * @return
     */
    public static List<Dictionary> findVocabulariesByIds(String ids) {
        From query = new Select().from(Dictionary.class);
        if (Objects.equals(ids, ""))
            return query.execute();
        else
            return query.where(String.format("Id IN (%s)", ids)).execute();
    }


    public static Dictionary findVocabularyWithAnswers(String vocabularyId, String excludes) {
        List<Dictionary> vocabularies = findVocabulariesByIds(vocabularyId);
        vocabularies.addAll(findVocabulariesWithExcludes(excludes, 3));

        for (int i = 0; i < vocabularies.size(); i++) {
            vocabularies.get(0).answers.add(vocabularies.get(i).en);
        }
        Collections.shuffle(vocabularies.get(0).answers);
        return vocabularies.get(0);
    }


    public static void setExamVocabularies(Context context, Boolean isNewExam, String... vocabulariesIds) {
        SharedPreferences prefs = context.getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);
        Set<String> vocabularies = new HashSet<>();

        if (!isNewExam && prefs.contains(LVoc.EXAM_VOCABULARY))
            vocabularies = prefs.getStringSet(LVoc.EXAM_VOCABULARY, new HashSet<String>());

        for (String id : vocabulariesIds) {
            if (!vocabularies.contains(id))
                vocabularies.add(id);
        }

        prefs.edit().putStringSet(LVoc.EXAM_VOCABULARY, vocabularies).apply();
    }


    public static Set<String> getExamVocabularies(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);
        return prefs.getStringSet(LVoc.EXAM_VOCABULARY, new HashSet<String>());
    }

    public static String pickExamVocabulary(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(LVoc.PACKAGE_NAME, Context.MODE_PRIVATE);
        Set<String> vocabularies = prefs.getStringSet(LVoc.EXAM_VOCABULARY, new HashSet<String>());
        Object id = vocabularies.toArray()[0];
        vocabularies.remove(id);
        prefs.edit().putStringSet(LVoc.EXAM_VOCABULARY, vocabularies).apply();
        return id.toString();
    }


    @Override
    public String toString() {
        return en + " : " + ar;
    }
}

