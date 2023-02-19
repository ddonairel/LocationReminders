package com.udacity.project4.locationreminders

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.udacity.project4.R
import com.udacity.project4.databinding.ActivityRemindersBinding

/**
 * The RemindersActivity that holds the reminders fragments
 */
class RemindersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRemindersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemindersBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.navHostFragment.findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        internal const val ACTION_GEOFENCE_EVENT =
            "RemindersActivity.action.ACTION_GEOFENCE_EVENT"
        internal const val REQUEST_FOREGROUND_AND_BACKGROUND_PERMISSION_RESULT_CODE = 33
        internal const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
        internal const val REQUEST_TURN_DEVICE_LOCATION_ON = 29
        internal const val TAG = "RemindersActivity"
        internal const val LOCATION_PERMISSION_INDEX = 0
        internal const val BACKGROUND_LOCATION_PERMISSION_INDEX = 1
    }
}
