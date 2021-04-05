package com.midtest.dictionary.model

class History(val id: Int, val languageIdFrom: String, val languageIdTo: String, val messageFrom: String, val messageTo: String, val dateTime: String, val user: String)

class Histories(val Histories: List<History>)

class HistoryResult(val History: History)
