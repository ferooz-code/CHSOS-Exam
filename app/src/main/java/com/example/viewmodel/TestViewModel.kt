package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.DataRepository
import com.example.db.AppDatabase
import com.example.db.TestAttempt
import com.example.models.Domain
import com.example.models.GlossaryTerm
import com.example.models.Question
import com.example.models.StudyTip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class TestViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataRepository(application)
    private val db = AppDatabase.getDatabase(application) // We need to add getDatabase in AppDatabase

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _domains = MutableStateFlow<List<Domain>>(emptyList())
    val domains: StateFlow<List<Domain>> = _domains.asStateFlow()

    private val _glossary = MutableStateFlow<List<GlossaryTerm>>(emptyList())
    val glossary: StateFlow<List<GlossaryTerm>> = _glossary.asStateFlow()

    private val _studyTips = MutableStateFlow<List<StudyTip>>(emptyList())
    val studyTips: StateFlow<List<StudyTip>> = _studyTips.asStateFlow()

    val testAttempts = db.testAttemptDao().getAllAttempts()

    init {
        viewModelScope.launch {
            _questions.value = repository.getQuestions()
            _domains.value = repository.getDomains()
            _glossary.value = repository.getGlossary()
            _studyTips.value = repository.getStudyTips()
        }
    }

    // Active Test State
    private val _activeSettings = MutableStateFlow(TestSettings())
    val activeSettings: StateFlow<TestSettings> = _activeSettings.asStateFlow()
    
    private val _currentTestQuestions = MutableStateFlow<List<Question>>(emptyList())
    val currentTestQuestions: StateFlow<List<Question>> = _currentTestQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _userAnswers = MutableStateFlow<Map<String, String>>(emptyMap())
    val userAnswers: StateFlow<Map<String, String>> = _userAnswers.asStateFlow()

    private val _flaggedQuestions = MutableStateFlow<Set<String>>(emptySet())
    val flaggedQuestions: StateFlow<Set<String>> = _flaggedQuestions.asStateFlow()

    var testStartTime: Long = 0
    var testTimeUsedMillis: Long = 0

    fun updateSettings(settings: TestSettings) {
        _activeSettings.value = settings
    }

    fun startTest() {
        val qList = _questions.value
        val filtered = if (_activeSettings.value.domain.isNotEmpty()) {
            qList.filter { it.domainNumber.toString() == _activeSettings.value.domain || it.domain == _activeSettings.value.domain }
        } else {
            qList
        }
        val shuffled = if (_activeSettings.value.randomize) filtered.shuffled() else filtered
        val toTake = shuffled.take(if (_activeSettings.value.questionCount > 0) _activeSettings.value.questionCount else qList.size)
        
        _currentTestQuestions.value = toTake
        _currentQuestionIndex.value = 0
        _userAnswers.value = emptyMap()
        _flaggedQuestions.value = emptySet()
        testStartTime = System.currentTimeMillis()
    }

    fun answerQuestion(questionId: String, answer: String) {
        _userAnswers.value = _userAnswers.value.toMutableMap().apply { put(questionId, answer) }
    }

    fun toggleFlag(questionId: String) {
        _flaggedQuestions.value = _flaggedQuestions.value.toMutableSet().apply {
            if (contains(questionId)) remove(questionId) else add(questionId)
        }
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < _currentTestQuestions.value.size - 1) {
            _currentQuestionIndex.value += 1
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
        }
    }

    fun submitTest() {
        testTimeUsedMillis = System.currentTimeMillis() - testStartTime
        viewModelScope.launch {
            val correctAnswersBase = _currentTestQuestions.value.filter {
                _userAnswers.value[it.id] == it.correctAnswer
            }
            val score = correctAnswersBase.size
            val domainScores = mutableMapOf<Int, Int>()
            correctAnswersBase.forEach {
                domainScores[it.domainNumber] = (domainScores[it.domainNumber] ?: 0) + 1
            }
            
            // simple json creation for domain scores
            val jsonObject = JSONObject()
            domainScores.forEach { (k, v) -> jsonObject.put(k.toString(), v) }

            db.testAttemptDao().insertAttempt(
                TestAttempt(
                    dateMillis = System.currentTimeMillis(),
                    score = score,
                    totalQuestions = _currentTestQuestions.value.size,
                    domainScoresJson = jsonObject.toString()
                )
            )
        }
    }
}

data class TestSettings(
    val questionCount: Int = 10,
    val timed: Boolean = false,
    val studyMode: Boolean = true,
    val randomize: Boolean = true,
    val domain: String = "", // empty means all
    val difficultyMix: String = "All"
)
