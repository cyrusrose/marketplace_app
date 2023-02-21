import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun View.setSheetVisibility(isVisible: Boolean) {
    val behavior = BottomSheetBehavior.from(this)
    behavior.state = if (isVisible)
        BottomSheetBehavior.STATE_COLLAPSED
    else BottomSheetBehavior.STATE_HIDDEN
}