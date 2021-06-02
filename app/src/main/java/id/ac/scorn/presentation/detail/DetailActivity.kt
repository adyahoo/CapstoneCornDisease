package id.ac.scorn.presentation.detail

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import id.ac.scorn.R
import id.ac.scorn.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val photo = intent.getStringExtra(EXTRA_IMG)
        val resultArray = intent.getFloatArrayExtra(EXTRA_ARRAY) as FloatArray

        binding.ivDetailCorn.setImageURI(Uri.parse(photo))
        binding.tvBlight.text = "Blight : ${resultArray[0]}"
        binding.tvRust.text = "Common Rust : ${resultArray[1]}"
        binding.tvGrayLeaf.text = "Gray Leaf Spot : ${resultArray[2]}"
        binding.tvHealthy.text = "Healthy : ${resultArray[3]}"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_IMG = "EXTRA_IMG"
        const val EXTRA_ARRAY = "EXTRA_ARRAY"
    }
}