package com.aljon.ipeople.features.person.list

import android.os.Bundle
import android.view.View
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelFragment
import com.aljon.ipeople.databinding.FragmentPersonListBinding
import com.aljon.ipeople.features.main.MainActivity

class PersonListFragment : BaseViewModelFragment<FragmentPersonListBinding, PersonListViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_person_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as MainActivity)
            .setToolbarTitle(getString(R.string.application_name))
    }
}
