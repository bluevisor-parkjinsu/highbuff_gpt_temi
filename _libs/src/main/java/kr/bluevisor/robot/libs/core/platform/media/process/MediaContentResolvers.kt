package kr.bluevisor.robot.libs.core.platform.media.process

import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import enn.libs.and.llog.LLog
import java.io.ByteArrayOutputStream

object MediaContentResolvers {
    fun getBitmapFromImageContentUri(uri: Uri, contentResolver: ContentResolver): Bitmap {
        contentResolver.openInputStream(uri).use {
            return BitmapFactory.decodeStream(it)
        }
    }

    fun getBase64EncodedBitmapPngBytesTextFromBitmap(bitmap: Bitmap): String {
        ByteArrayOutputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            val encodedText = Base64.encodeToString(it.toByteArray(), Base64.DEFAULT)
            return "data:image/png;base64,$encodedText"
        }
    }

    fun getBase64EncodedBitmapPngBytesTextFromImageContentUri(
        uri: Uri,
        contentResolver: ContentResolver
    ): String {
        val bitmap = getBitmapFromImageContentUri(uri, contentResolver)
        return getBase64EncodedBitmapPngBytesTextFromBitmap(bitmap)
    }

    fun getIdFromLastPathSegmentOfContentUri(uri: Uri) =
        uri.lastPathSegment?.toLongOrNull()

    fun getContentUriById(id: Long, contextResolver: ContentResolver) =
        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

    fun getDisplayNameFromContentUri(uri: Uri, contentResolver: ContentResolver): String? {
        var displayName: String? = null
        val cursor = contentResolver.query(
            uri, null, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                val nameColumnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                displayName = it.getString(nameColumnIndex)
            }
            LLog.w("cursor.moveToFirst() is failed.")
        }

        LLog.v("displayName: $displayName, uri: $uri.")
        return displayName
    }
}