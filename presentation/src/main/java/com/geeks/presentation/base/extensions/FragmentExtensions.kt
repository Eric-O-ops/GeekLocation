package com.geeks.presentation.base.extensions

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.geeks.domain.models.LocModelDomain
import com.geeks.presentation.R
import com.geeks.presentation.models.LocModelUI
import com.geeks.presentation.models.toUI

fun Fragment.toast(text: String) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_SHORT).show()
}
fun Fragment.toast(text: Int) {
    Toast.makeText(this.requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.showAlertDialog(title: Int, massage: Int) {
    AlertDialog.Builder(requireContext())
        .setCancelable(false)
        .setTitle(title)
        .setMessage(massage)
        .setPositiveButton(R.string.ok) { _, _ -> }
        .create().show()
}

fun Fragment.checkSinglePermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun listToListUI(list: ArrayList<LocModelDomain>): ArrayList<LocModelUI> {
    return list.map { it.toUI() } as ArrayList<LocModelUI>
}