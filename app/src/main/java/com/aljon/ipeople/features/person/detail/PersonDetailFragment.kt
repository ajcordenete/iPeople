package com.aljon.ipeople.features.person.detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseFragment
import com.aljon.ipeople.databinding.FragmentPersonDetailBinding

class PersonDetailFragment : BaseFragment<FragmentPersonDetailBinding>() {

    private val args by navArgs<PersonDetailFragmentArgs>()

    override fun getLayoutId(): Int = R.layout.fragment_person_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setUpData()
    }

    private fun setupToolbar() {
        setToolbarNoTitle()
    }

    private fun setUpData() {
        val person = args.person

        binding.item = person
    }

    override fun canBack(): Boolean = true
}
