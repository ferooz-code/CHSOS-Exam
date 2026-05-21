package com.example.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    val id: String,
    val question: String,
    val options: Map<String, String>,
    val correctAnswer: String,
    val rationale: String,
    val whyIncorrect: Map<String, String>? = null,
    val domain: String,
    val domainNumber: Int,
    val difficulty: String = "moderate",
    val cognitiveLevel: String = "application",
    val whyThisMatters: String = ""
)

@JsonClass(generateAdapter = true)
data class Domain(
    val id: String,
    val name: String,
    val weight: Int,
    val description: String,
    val keyKsa: String
)

@JsonClass(generateAdapter = true)
data class GlossaryTerm(
    val term: String,
    val definition: String
)

@JsonClass(generateAdapter = true)
data class StudyTip(
    val id: Int,
    val tip: String
)
