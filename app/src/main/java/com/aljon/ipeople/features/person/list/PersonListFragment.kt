package com.aljon.ipeople.features.person.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelFragment
import com.aljon.ipeople.databinding.FragmentPersonListBinding
import com.aljon.ipeople.features.auth.login.LoginActivity
import com.aljon.ipeople.features.person.adapter.PersonAdapter
import com.aljon.module.common.gone
import com.aljon.module.common.showAlertDialog
import com.aljon.module.common.toast
import com.aljon.module.common.visible
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class PersonListFragment : BaseViewModelFragment<FragmentPersonListBinding, PersonListViewModel>() {

    private val personAdapter by lazy {
        PersonAdapter(listOf())
    }

    override fun getLayoutId(): Int = R.layout.fragment_person_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setUpViews()
        setUpViewModel()
        setHasOptionsMenu(true)
    }

    private fun setupToolbar() {
        setToolbarNoTitle()
    }

    private fun setUpViews() {
        personAdapter
            .itemClickListener
            .subscribe { person ->
            }
            .apply { disposables.add(this) }

        binding
            .personList
            .apply {
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = personAdapter
            }
    }

    private fun setUpViewModel() {
        viewModel
            .state
            .observeOn(scheduler.ui())
            .subscribeBy(
                onNext = { state ->
                    handleState(state)
                },
                onError = {
                    Timber.e(it)
                }
            )
            .apply { disposables.add(this) }

        viewModel
            .persons
            .observe(this, Observer {
                personAdapter.updateList(it)
                binding.contactsCount.text = getString(
                    R.string.you_have_contacts,
                    it.size.toString()
                )
            })
    }

    private fun handleState(state: PersonListState) {
        when (state) {
            is PersonListState.GetName -> {
                binding.title.text = getString(
                    R.string.welcome,
                    state.name
                )
            }

            is PersonListState.ShowLoading -> {
                binding.loading.visible()
            }

            is PersonListState.HideLoading -> {
                binding.loading.gone()
            }

            is PersonListState.ShowFetchError -> {
                activity?.toast(getString(R.string.generic_error))
            }

            is PersonListState.LogoutSuccessful -> {
                LoginActivity.openActivity(requireContext())
                activity?.finishAffinity()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                showLogoutDialog()
                return true
            }

            else -> false
        }
    }

    private fun showLogoutDialog() {
        (activity)?.showAlertDialog(
            R.string.logout,
            R.string.logout_message,
            R.string.logout,
            viewModel::logout
        )
    }
}
