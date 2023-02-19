package com.udacity.project4.locationreminders.savereminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.R
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.getOrAwaitValue
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var saveReminderViewModel: SaveReminderViewModel
    private lateinit var dataSource: FakeDataSource
    private val reminderDataItem = ReminderDataItem(
        "Hospital",
        "Hospital de Sant Joan Despí",
        "Hospital de San Juan Despí",
        41.37331648522115,
        2.072516083717346
    )

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        dataSource = FakeDataSource()
        saveReminderViewModel =
            SaveReminderViewModel(ApplicationProvider.getApplicationContext(), dataSource)
        stopKoin()
    }

    @Test
    fun addReminder_checkLoading() {
        mainCoroutineRule.pauseDispatcher()

        saveReminderViewModel.saveReminder(reminderDataItem)

        assertThat(saveReminderViewModel.showLoading.getOrAwaitValue(), `is`(true))

        mainCoroutineRule.resumeDispatcher()

        assertThat(saveReminderViewModel.showLoading.getOrAwaitValue(), `is`(false))

    }

    @Test
    fun check_Snackbar_error() {
        dataSource.setReturnError(true)
        val reminderDataItemNoTitle = ReminderDataItem(
            null,
            "Hospital de Sant Joan Despí",
            "Hospital de San Juan Despí",
            41.37331648522115,
            2.072516083717346
        )
        saveReminderViewModel.validateAndSaveReminder(reminderDataItemNoTitle)
        assertThat(
            saveReminderViewModel.showSnackBarInt.getOrAwaitValue(),
            `is`(R.string.err_enter_title)
        )

        val reminderDataItemNoLocation = ReminderDataItem(
            "Hospital",
            "Hospital de Sant Joan Despí",
            null,
            41.37331648522115,
            2.072516083717346
        )
        saveReminderViewModel.validateAndSaveReminder(reminderDataItemNoLocation)
        assertThat(
            saveReminderViewModel.showSnackBarInt.getOrAwaitValue(),
            `is`((R.string.err_select_location))
        )

    }

}