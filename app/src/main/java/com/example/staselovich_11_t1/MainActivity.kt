package com.example.staselovich_11_t1

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.staselovich_11_t1.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var bunding: ActivityMainBinding
    var pageHeight = 1120
    var pageWreight = 792
    val CONSTATE = 1
    lateinit var  bmp: Bitmap
    lateinit var  scaledBimap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bunding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bunding.root)
        bmp = BitmapFactory.decodeResource(resources, R.drawable.cat)
        scaledBimap = Bitmap.createScaledBitmap(bmp, 140, 140, false)

        if (checkPermission()){
           Toast.makeText(this, "Permision Grated", Toast.LENGTH_SHORT).show()
       }else {
           requestPermission()
       }


        sendPDF()
    }

    fun sendPDF() {
        bunding.button.setOnClickListener {
            generatePdf()
        }
    }
    private fun generatePdf() {
        val pdfDocument = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            PdfDocument()
        } else {
            TODO("VERSION.SDK_INT < KITKAT")
        }
        val paint = Paint()
        val title = Paint()
        val mypageInfo = PdfDocument.PageInfo.Builder(pageWreight, pageHeight, 1).create()
        val myPage = pdfDocument.startPage(mypageInfo)
        val canvas: Canvas = myPage.canvas
        canvas.drawBitmap(scaledBimap, 56f,40f,  paint)
        title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        title.textSize = 45f
        title.color = ContextCompat.getColor(this, R.color.black)
        canvas.drawText(bunding.editTextTextPersonName.text.toString(), 209f, 80f, title)
        canvas.drawText(bunding.editTextTextPersonName2.text.toString(), 209f, 130f, title)
        canvas.drawText(bunding.editTextTextPersonName3.text.toString(), 209f, 300f, title)



        pdfDocument.finishPage(myPage)
        val file = File(Environment.getExternalStorageDirectory(), "GFG.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "PDF file generated succesfully.", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            Toast.makeText(this, " " + e, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        pdfDocument.close()
    }
    private fun checkPermission(): Boolean {
        val permission1 =
            ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), CONSTATE)
    }
}
