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

    private val blueFace = listOf(
        FaceExpression(name = "Blue Good", image = R.drawable.face__blue__good),
        FaceExpression(name = "Blue Normal", image = R.drawable.face__blue__normal),
        FaceExpression(name = "Blue Processing", image = R.drawable.face__blue__processing),
        FaceExpression(name = "Blue Speaking", image = R.drawable.face__blue__speaking),
        FaceExpression(name = "Blue Wait", image = R.drawable.face__blue__wait)
    )
    private val catFace = listOf(
        FaceExpression(name = "Cat Good", image = R.drawable.face__cat__good),
        FaceExpression(name = "Cat Normal", image = R.drawable.face__cat__normal),
        FaceExpression(name = "Cat Processing", image = R.drawable.face__cat__processing),
        FaceExpression(name = "Cat Speaking", image = R.drawable.face__cat__speaking),
        FaceExpression(name = "Cat Wait", image = R.drawable.face__cat__wait)
    )
    private val defaultFace = listOf(
        FaceExpression(name = "Default Good", image = R.drawable.face__default__good),
        FaceExpression(name = "Default Normal", image = R.drawable.face__default__normal),
        FaceExpression(name = "Default Processing", image = R.drawable.face__default__processing),
        FaceExpression(name = "Default Speaking", image = R.drawable.face__default__speaking),
        FaceExpression(name = "Default Wait", image = R.drawable.face__default__wait)
    )
    private val freckleFace = listOf(
        FaceExpression(name = "Freckle Good", image = R.drawable.face__freckle__good),
        FaceExpression(name = "Freckle Normal", image = R.drawable.face__freckle__normal),
        FaceExpression(name = "Freckle Processing", image = R.drawable.face__default__processing),
        FaceExpression(name = "Freckle Speaking", image = R.drawable.face__freckle__speaking),
        FaceExpression(name = "Freckle Wait", image = R.drawable.face__freckle__wait)
    )

    private val neonFace = listOf(
        FaceExpression(name = "Neon Good", image = R.drawable.face__neon__good),
        FaceExpression(name = "Neon Normal", image = R.drawable.face__neon__normal),
        FaceExpression(name = "Neon Processing", image = R.drawable.face__neon__processing),
        FaceExpression(name = "Neon Speaking", image = R.drawable.face__neon__speaking),
        FaceExpression(name = "Neon Wait", image = R.drawable.face__neon__wait)
    )
    private val neonLightFace = listOf(
        FaceExpression(name = "Nightlight Good", image = R.drawable.face__nightlight__good),
        FaceExpression(name = "Nightlight Normal", image = R.drawable.face__nightlight__normal),
        FaceExpression(name = "Nightlight Processing", image = R.drawable.face__neon__processing),
        FaceExpression(name = "Nightlight Speaking", image = R.drawable.face__nightlight__speaking),
        FaceExpression(name = "Nightlight Wait", image = R.drawable.face__nightlight__wait)
    )
    private val owlFace = listOf(
        FaceExpression(name = "Owl Good", image = R.drawable.face__owl__good),
        FaceExpression(name = "Owl Normal", image = R.drawable.face__owl__normal),
        FaceExpression(name = "Owl Processing", image = R.drawable.face__owl__processing),
        FaceExpression(name = "Owl Speaking", image = R.drawable.face__owl__speaking),
        FaceExpression(name = "Owl Wait", image = R.drawable.face__owl__wait)
    )
    private val robotFace = listOf(
        FaceExpression(name = "Robot Good", image = R.drawable.face__robot__good),
        FaceExpression(name = "Robot Normal", image = R.drawable.face__robot__normal),
        FaceExpression(name = "Robot Processing", image = R.drawable.face__robot__processing),
        FaceExpression(name = "Robot Speaking", image = R.drawable.face__robot__speaking),
        FaceExpression(name = "Robot Wait", image = R.drawable.face__robot__wait)
    )
    private val whiteFace = listOf(
        FaceExpression(name = "White Good", image = R.drawable.face__white__good),
        FaceExpression(name = "White Normal", image = R.drawable.face__white__normal),
        FaceExpression(name = "White Processing", image = R.drawable.face__white__processing),
        FaceExpression(name = "White Speaking", image = R.drawable.face__white__speaking),
        FaceExpression(name = "White Wait", image = R.drawable.face__white__wait)
    )
    private val yellowFace = listOf(
        FaceExpression(name = "Yellow Good", image = R.drawable.face__yellow__good),
        FaceExpression(name = "Yellow Normal", image = R.drawable.face__yellow__normal),
        FaceExpression(name = "Yellow Processing", image = R.drawable.face__yellow__processing),
        FaceExpression(name = "Yellow Speaking", image = R.drawable.face__yellow__speaking),
        FaceExpression(name = "Yellow Wait", image = R.drawable.face__yellow__wait)
    )
    private val simpleFace = listOf(
        FaceExpression(name = "Simple Depressed", image = R.drawable.face_ext__simple__depressed),
        FaceExpression(name = "Simple Imagine", image = R.drawable.face_ext__simple__imagine),
        FaceExpression(name = "Simple Moving", image = R.drawable.face_ext__simple__moving),
        FaceExpression(
            name = "Simple playing Music",
            image = R.drawable.face_ext__simple__playing_music
        ),
        FaceExpression(name = "Simple Sad", image = R.drawable.face_ext__simple__sad),
        FaceExpression(name = "Simple Sulky", image = R.drawable.face_ext__simple__sulky)
    )

    val allFaceExpressions = listOf(
        blueFace,
        catFace,
        defaultFace,
        freckleFace,
        neonFace,
        neonLightFace,
        owlFace,
        robotFace,
        whiteFace,
        yellowFace,
        simpleFace
    ).flatten()

    fun getFaceList(faceExpression: FaceExpression): List<FaceExpression> {
        return when {
            faceExpression.name.startsWith("Blue") -> blueFace
            faceExpression.name.startsWith("Cat") -> catFace
            faceExpression.name.startsWith("Default") -> defaultFace
            faceExpression.name.startsWith("Freckle") -> freckleFace
            faceExpression.name.startsWith("Neon") -> neonFace
            faceExpression.name.startsWith("Nightlight") -> neonLightFace
            faceExpression.name.startsWith("Owl") -> owlFace
            faceExpression.name.startsWith("Robot") -> robotFace
            faceExpression.name.startsWith("White") -> whiteFace
            faceExpression.name.startsWith("Yellow") -> yellowFace
            faceExpression.name.startsWith("Simple") -> simpleFace
            else -> emptyList()
        }

    }

    private val _image = MutableLiveData<Int>()
    val image: LiveData<Int> get() = _image

    fun onItemClicked(navController: NavController,faceExpression: FaceExpression) {
        _image.value = faceExpression.image
        navController.navigate(MainActivity.SCREEN__MAIN)
    }

    private val _selectedFace = MutableLiveData<FaceExpression>()
    val selectedFace: LiveData<FaceExpression> = _selectedFace

    fun onDetailsItemClicked(navController: NavController,faceExpression: FaceExpression) {
        _selectedFace.value = faceExpression
        navController.navigate(MainActivity.SCREEN__CHANGE_FACE_DETAILS)
    }
}@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val blueFace = listOf(
        FaceExpression(name = "Blue Good", image = R.drawable.face__blue__good),
        FaceExpression(name = "Blue Normal", image = R.drawable.face__blue__normal),
        FaceExpression(name = "Blue Processing", image = R.drawable.face__blue__processing),
        FaceExpression(name = "Blue Speaking", image = R.drawable.face__blue__speaking),
        FaceExpression(name = "Blue Wait", image = R.drawable.face__blue__wait)
    )
    private val catFace = listOf(
        FaceExpression(name = "Cat Good", image = R.drawable.face__cat__good),
        FaceExpression(name = "Cat Normal", image = R.drawable.face__cat__normal),
        FaceExpression(name = "Cat Processing", image = R.drawable.face__cat__processing),
        FaceExpression(name = "Cat Speaking", image = R.drawable.face__cat__speaking),
        FaceExpression(name = "Cat Wait", image = R.drawable.face__cat__wait)
    )
    private val defaultFace = listOf(
        FaceExpression(name = "Default Good", image = R.drawable.face__default__good),
        FaceExpression(name = "Default Normal", image = R.drawable.face__default__normal),
        FaceExpression(name = "Default Processing", image = R.drawable.face__default__processing),
        FaceExpression(name = "Default Speaking", image = R.drawable.face__default__speaking),
        FaceExpression(name = "Default Wait", image = R.drawable.face__default__wait)
    )
    private val freckleFace = listOf(
        FaceExpression(name = "Freckle Good", image = R.drawable.face__freckle__good),
        FaceExpression(name = "Freckle Normal", image = R.drawable.face__freckle__normal),
        FaceExpression(name = "Freckle Processing", image = R.drawable.face__default__processing),
        FaceExpression(name = "Freckle Speaking", image = R.drawable.face__freckle__speaking),
        FaceExpression(name = "Freckle Wait", image = R.drawable.face__freckle__wait)
    )

    private val neonFace = listOf(
        FaceExpression(name = "Neon Good", image = R.drawable.face__neon__good),
        FaceExpression(name = "Neon Normal", image = R.drawable.face__neon__normal),
        FaceExpression(name = "Neon Processing", image = R.drawable.face__neon__processing),
        FaceExpression(name = "Neon Speaking", image = R.drawable.face__neon__speaking),
        FaceExpression(name = "Neon Wait", image = R.drawable.face__neon__wait)
    )
    private val neonLightFace = listOf(
        FaceExpression(name = "Nightlight Good", image = R.drawable.face__nightlight__good),
        FaceExpression(name = "Nightlight Normal", image = R.drawable.face__nightlight__normal),
        FaceExpression(name = "Nightlight Processing", image = R.drawable.face__neon__processing),
        FaceExpression(name = "Nightlight Speaking", image = R.drawable.face__nightlight__speaking),
        FaceExpression(name = "Nightlight Wait", image = R.drawable.face__nightlight__wait)
    )
    private val owlFace = listOf(
        FaceExpression(name = "Owl Good", image = R.drawable.face__owl__good),
        FaceExpression(name = "Owl Normal", image = R.drawable.face__owl__normal),
        FaceExpression(name = "Owl Processing", image = R.drawable.face__owl__processing),
        FaceExpression(name = "Owl Speaking", image = R.drawable.face__owl__speaking),
        FaceExpression(name = "Owl Wait", image = R.drawable.face__owl__wait)
    )
    private val robotFace = listOf(
        FaceExpression(name = "Robot Good", image = R.drawable.face__robot__good),
        FaceExpression(name = "Robot Normal", image = R.drawable.face__robot__normal),
        FaceExpression(name = "Robot Processing", image = R.drawable.face__robot__processing),
        FaceExpression(name = "Robot Speaking", image = R.drawable.face__robot__speaking),
        FaceExpression(name = "Robot Wait", image = R.drawable.face__robot__wait)
    )
    private val whiteFace = listOf(
        FaceExpression(name = "White Good", image = R.drawable.face__white__good),
        FaceExpression(name = "White Normal", image = R.drawable.face__white__normal),
        FaceExpression(name = "White Processing", image = R.drawable.face__white__processing),
        FaceExpression(name = "White Speaking", image = R.drawable.face__white__speaking),
        FaceExpression(name = "White Wait", image = R.drawable.face__white__wait)
    )
    private val yellowFace = listOf(
        FaceExpression(name = "Yellow Good", image = R.drawable.face__yellow__good),
        FaceExpression(name = "Yellow Normal", image = R.drawable.face__yellow__normal),
        FaceExpression(name = "Yellow Processing", image = R.drawable.face__yellow__processing),
        FaceExpression(name = "Yellow Speaking", image = R.drawable.face__yellow__speaking),
        FaceExpression(name = "Yellow Wait", image = R.drawable.face__yellow__wait)
    )
    private val simpleFace = listOf(
        FaceExpression(name = "Simple Depressed", image = R.drawable.face_ext__simple__depressed),
        FaceExpression(name = "Simple Imagine", image = R.drawable.face_ext__simple__imagine),
        FaceExpression(name = "Simple Moving", image = R.drawable.face_ext__simple__moving),
        FaceExpression(
            name = "Simple playing Music",
            image = R.drawable.face_ext__simple__playing_music
        ),
        FaceExpression(name = "Simple Sad", image = R.drawable.face_ext__simple__sad),
        FaceExpression(name = "Simple Sulky", image = R.drawable.face_ext__simple__sulky)
    )

    val allFaceExpressions = listOf(
        blueFace,
        catFace,
        defaultFace,
        freckleFace,
        neonFace,
        neonLightFace,
        owlFace,
        robotFace,
        whiteFace,
        yellowFace,
        simpleFace
    ).flatten()

    fun getFaceList(faceExpression: FaceExpression): List<FaceExpression> {
        return when {
            faceExpression.name.startsWith("Blue") -> blueFace
            faceExpression.name.startsWith("Cat") -> catFace
            faceExpression.name.startsWith("Default") -> defaultFace
            faceExpression.name.startsWith("Freckle") -> freckleFace
            faceExpression.name.startsWith("Neon") -> neonFace
            faceExpression.name.startsWith("Nightlight") -> neonLightFace
            faceExpression.name.startsWith("Owl") -> owlFace
            faceExpression.name.startsWith("Robot") -> robotFace
            faceExpression.name.startsWith("White") -> whiteFace
            faceExpression.name.startsWith("Yellow") -> yellowFace
            faceExpression.name.startsWith("Simple") -> simpleFace
            else -> emptyList()
        }

    }

    private val _image = MutableLiveData<Int>()
    val image: LiveData<Int> get() = _image

    fun onItemClicked(navController: NavController,faceExpression: FaceExpression) {
        _image.value = faceExpression.image
        navController.navigate(MainActivity.SCREEN__MAIN)
    }

    private val _selectedFace = MutableLiveData<FaceExpression>()
    val selectedFace: LiveData<FaceExpression> = _selectedFace

    fun onDetailsItemClicked(navController: NavController,faceExpression: FaceExpression) {
        _selectedFace.value = faceExpression
        navController.navigate(MainActivity.SCREEN__CHANGE_FACE_DETAILS)
    }
}