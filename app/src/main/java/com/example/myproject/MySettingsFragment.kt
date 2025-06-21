package com.example.myproject

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat

class MySettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val namePreference: EditTextPreference? = findPreference("nickname")
        val colorPreference : ListPreference? = findPreference("color")

        namePreference?.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
    }
}
