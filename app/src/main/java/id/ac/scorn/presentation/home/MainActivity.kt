package id.ac.scorn.presentation.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.ac.scorn.databinding.ActivityMainBinding
import id.ac.scorn.domain.Photo
import id.ac.scorn.ml.CapCornDiseaseDetectionModel2
import id.ac.scorn.presentation.detail.DetailActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentPhotoPath: String
    private lateinit var currentPhotoUri: Uri
    private lateinit var selectedPhotoUri: Uri
    private lateinit var selectedBitmap: Bitmap
    private lateinit var photoAdapter: PhotoAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        }

        photoAdapter = PhotoAdapter()
        viewModel.getAllPhotos().observe(this, { photos ->
            if (photos != null) {
                photoAdapter.setData(photos)
            }
        })

        with(binding.rvMain) {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            setHasFixedSize(true)
            adapter = photoAdapter
        }

        binding.btnCapture.setOnClickListener {
            takePictureIntent()
        }

        binding.btnGallery.setOnClickListener {
            getPhotoFromGalleryIntent()
        }

        photoAdapter.onItemClick = { selectedData ->
            tfModel(selectedData)
        }
    }

    private fun getPhotoFromGalleryIntent() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"

        startActivityForResult(intent, 101)
    }

    private fun tfModel(data: Photo) {
        selectedPhotoUri = Uri.parse(data.photo)
        selectedBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedPhotoUri)

        val resized = Bitmap.createScaledBitmap(selectedBitmap, 224, 224, true)

        val model = CapCornDiseaseDetectionModel2.newInstance(this)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        val tImage = TensorImage(DataType.FLOAT32)
        tImage.load(resized)
        val byteBuffer = tImage.buffer
        inputFeature0.loadBuffer(byteBuffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_IMG, data.photo)
        intent.putExtra(DetailActivity.EXTRA_ARRAY, outputFeature0.floatArray)
        startActivity(intent)

        model.close()
    }

    private fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f

        for (i in arr.indices) {
            if (arr[i] > min) {
                ind = i
                min = arr[i]
            }
        }
        return ind
    }

    private fun takePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile = createImageFile()
                photoFile.also {
                    currentPhotoUri = FileProvider.getUriForFile(
                        this,
                        "id.ac.scorn.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            val photo = Photo(null, currentPhotoUri.toString())
            viewModel.insertPhoto(photo)
        }else if (requestCode == REQUEST_GALLERY_IMAGE) {
            val uri = data?.data
            val photo = Photo(null, uri.toString())
            viewModel.insertPhoto(photo)
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 100
        const val REQUEST_GALLERY_IMAGE = 101
    }
}