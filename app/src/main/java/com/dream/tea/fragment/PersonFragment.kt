package com.dream.tea.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dream.tea.R
import com.dream.tea.activity.DeveloperActivity
import com.dream.tea.activity.FeedbackActivity

class PersonFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.navigation_person_fragment, container, false)
        // 开发者页面
        val developerTextView: TextView = view.findViewById(R.id.developer_tab)
        developerTextView.setOnClickListener {
            DeveloperActivity.actionStart(view.context)
        }
        // 意见反馈页面
        val feedbackTextView: TextView = view.findViewById(R.id.feedback_tab)
        feedbackTextView.setOnClickListener {
            FeedbackActivity.actionStart(view.context)
        }
        return view
    }


}