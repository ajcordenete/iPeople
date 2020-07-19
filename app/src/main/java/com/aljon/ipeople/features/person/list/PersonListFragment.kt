package com.aljon.ipeople.features.person.list

import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelFragment
import com.aljon.ipeople.databinding.FragmentPersonListBinding
import kotlinx.android.synthetic.main.fragment_person_list.view.*

class PersonListFragment : BaseViewModelFragment<FragmentPersonListBinding, PersonListViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_person_list
}
