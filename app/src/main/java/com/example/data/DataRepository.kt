package com.example.data

import android.content.Context
import com.example.models.Domain
import com.example.models.GlossaryTerm
import com.example.models.Question
import com.example.models.StudyTip
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DataRepository(private val context: Context) {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun getQuestions(): List<Question> {
        val json = context.assets.open("data/questions.json").bufferedReader().use { it.readText() }
        val type = Types.newParameterizedType(List::class.java, Question::class.java)
        val adapter = moshi.adapter<List<Question>>(type)
        return adapter.fromJson(json) ?: emptyList()
    }

    fun getDomains(): List<Domain> {
        val json = context.assets.open("data/domains.json").bufferedReader().use { it.readText() }
        val type = Types.newParameterizedType(List::class.java, Domain::class.java)
        val adapter = moshi.adapter<List<Domain>>(type)
        return adapter.fromJson(json) ?: emptyList()
    }

    fun getGlossary(): List<GlossaryTerm> {
        val json = context.assets.open("data/glossary.json").bufferedReader().use { it.readText() }
        val type = Types.newParameterizedType(List::class.java, GlossaryTerm::class.java)
        val adapter = moshi.adapter<List<GlossaryTerm>>(type)
        return adapter.fromJson(json) ?: emptyList()
    }

    fun getStudyTips(): List<StudyTip> {
        val json = context.assets.open("data/studyTips.json").bufferedReader().use { it.readText() }
        val type = Types.newParameterizedType(List::class.java, StudyTip::class.java)
        val adapter = moshi.adapter<List<StudyTip>>(type)
        return adapter.fromJson(json) ?: emptyList()
    }
}
