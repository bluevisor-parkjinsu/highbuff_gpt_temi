package kr.bluevisor.robot.highbuff_gpt_temi.presentation.model


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import enn.libs.and.llog.LLog
import kr.bluevisor.robot.highbuff_gpt_temi.R
import kr.bluevisor.robot.highbuff_gpt_temi.domain.entity.FaceExpression
import kr.bluevisor.robot.highbuff_gpt_temi.presentation.ui.MainActivity
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    val faceExpressions = listOf(
        FaceExpression(id = 1, name = "Blue Good", image = R.drawable.face__blue__good),
        FaceExpression(id = 2, name = "Blue Normal", image = R.drawable.face__blue__normal),
        FaceExpression(id = 3, name = "Blue Processing", image = R.drawable.face__blue__processing),
        FaceExpression(id = 4, name = "Blue Speaking", image = R.drawable.face__blue__speaking),
        FaceExpression(id = 5, name = "Cat Good", image = R.drawable.face__cat__good),
        FaceExpression(id = 6, name = "Cat Normal", image = R.drawable.face__cat__normal),
        FaceExpression(id = 7, name = "Cat Processing", image = R.drawable.face__cat__processing),
        FaceExpression(id = 8, name = "Cat Speaking", image = R.drawable.face__cat__speaking),
        FaceExpression(id = 9, name = "Cat Wait", image = R.drawable.face__cat__wait),
        FaceExpression(id = 10, name = "Default Good", image = R.drawable.face__default__good),
        FaceExpression(id = 11, name = "Default Normal", image = R.drawable.face__default__normal),
        FaceExpression(id = 12, name = "Default Processing", image = R.drawable.face__default__processing),
        FaceExpression(id = 13, name = "Default Speaking", image = R.drawable.face__default__speaking),
        FaceExpression(id = 14, name = "Default Wait", image = R.drawable.face__default__wait),
        FaceExpression(id = 15, name = "Freckle Good", image = R.drawable.face__freckle__good),
        FaceExpression(id = 16, name = "Freckle Normal", image = R.drawable.face__freckle__normal),
        FaceExpression(id = 17, name = "Freckle Processing", image = R.drawable.face__default__processing),
        FaceExpression(id = 18, name = "Freckle Speaking", image = R.drawable.face__freckle__speaking),
        FaceExpression(id = 19, name = "Freckle Wait", image = R.drawable.face__freckle__wait),
        FaceExpression(id = 20, name = "Neon Good", image = R.drawable.face__neon__good),
        FaceExpression(id = 21, name = "Neon Normal", image = R.drawable.face__neon__normal),
        FaceExpression(id = 22, name = "Neon Processing", image = R.drawable.face__neon__processing),
        FaceExpression(id = 23, name = "Neon Speaking", image = R.drawable.face__neon__speaking),
        FaceExpression(id = 24, name = "Neon Wait", image = R.drawable.face__neon__wait),
        FaceExpression(id = 25, name = "Nightlight Good", image = R.drawable.face__nightlight__good),
        FaceExpression(id = 26, name = "Nightlight Normal", image = R.drawable.face__nightlight__normal),
        FaceExpression(id = 27, name = "Nightlight Processing", image = R.drawable.face__neon__processing),
        FaceExpression(id = 28, name = "Nightlight Speaking", image = R.drawable.face__nightlight__speaking),
        FaceExpression(id = 29, name = "Owl Good", image = R.drawable.face__owl__good),
        FaceExpression(id = 30, name = "Owl Normal", image = R.drawable.face__owl__normal),
        FaceExpression(id = 31, name = "Owl Processing", image = R.drawable.face__owl__processing),
        FaceExpression(id = 32, name = "Owl Speaking", image = R.drawable.face__owl__speaking),
        FaceExpression(id = 33, name = "Owl Wait", image = R.drawable.face__owl__wait),
        FaceExpression(id = 34, name = "Robot Good", image = R.drawable.face__robot__good),
        FaceExpression(id = 35, name = "Robot Normal", image = R.drawable.face__robot__normal),
        FaceExpression(id = 36, name = "Robot Processing", image = R.drawable.face__robot__processing),
        FaceExpression(id = 37, name = "Robot Speaking", image = R.drawable.face__robot__speaking),
        FaceExpression(id = 38, name = "Robot Wait", image = R.drawable.face__robot__wait),
        FaceExpression(id = 39, name = "White Good", image = R.drawable.face__white__good),
        FaceExpression(id = 40, name = "White Normal", image = R.drawable.face__white__normal),
        FaceExpression(id = 41, name = "White Processing", image = R.drawable.face__white__processing),
        FaceExpression(id = 42, name = "White Speaking", image = R.drawable.face__white__speaking),
        FaceExpression(id = 43, name = "White Wait", image = R.drawable.face__white__wait),
        FaceExpression(id = 44, name = "Yellow Good", image = R.drawable.face__yellow__good),
        FaceExpression(id = 45, name = "Yellow Normal", image = R.drawable.face__yellow__normal),
        FaceExpression(id = 46, name = "Yellow Processing", image = R.drawable.face__yellow__processing),
        FaceExpression(id = 47, name = "Yellow Speaking", image = R.drawable.face__yellow__speaking),
        FaceExpression(id = 48, name = "Yellow Wait", image = R.drawable.face__yellow__wait),
        FaceExpression(id = 49, name = "Simple Depressed", image = R.drawable.face_ext__simple__depressed),
        FaceExpression(id = 50, name = "Simple Imagine", image = R.drawable.face_ext__simple__imagine),
        FaceExpression(id = 51, name = "Simple Moving", image = R.drawable.face_ext__simple__moving),
        FaceExpression(id = 52, name = "Simple playing Music", image = R.drawable.face_ext__simple__playing_music),
        FaceExpression(id = 53, name = "Simple Sad", image = R.drawable.face_ext__simple__sad),
        FaceExpression(id = 54, name = "Simple Sulky", image = R.drawable.face_ext__simple__sulky),
    )

    // MutableLiveData 선언
    private val _imageUri = MutableLiveData<Int>(R.drawable.face__cat__normal)
    val imageUri: LiveData<Int> get() = _imageUri

    fun onItemClicked(faceExpression: FaceExpression, navController: NavController) {
        _imageUri.value = faceExpression.image
        navController.popBackStack()
        navController.navigate(MainActivity.SCREEN__PROMOTION)
        LLog.w(imageUri.value.toString())
    }
}