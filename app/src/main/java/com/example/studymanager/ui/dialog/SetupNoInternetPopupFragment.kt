package be.hogent.teamoverkill.fietsapp.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.studymanager.R
import kotlin.system.exitProcess

/**
 * Popup weergave wanneer de emulator geen connectie met het internet kan maken
 */
class SetupNoInternetPopupFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.title_error_loading)
                .setMessage(R.string.error_loading_nonetwork)
                .setPositiveButton(R.string.action_error_loading) { _, _ ->
                    activity?.finishAffinity()
                    exitProcess(-1)
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(): SetupNoInternetPopupFragment {
            return SetupNoInternetPopupFragment()
        }
    }
}