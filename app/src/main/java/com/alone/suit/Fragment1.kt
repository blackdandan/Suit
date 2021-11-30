package com.alone.suit

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var surfaceView: SurfaceView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ")
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        initCamera(false)
    }
    private fun initCamera(front:Boolean){
        val cameraProviderFeature = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFeature.addListener({
            val cameraProvider = cameraProviderFeature.get()
            bindPreview(cameraProvider,front)
            Log.d(TAG, "onViewCreated: CameraListenerRunnable")
        }, ContextCompat.getMainExecutor(requireContext()))
    }
    private fun bindPreview(cameraProvider: ProcessCameraProvider?,front:Boolean) {
        val preview = Preview.Builder().build()
        val cameraSelectorBack = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        val cameraSelectorFront = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build()
        val cameraSelector = (if (front) cameraSelectorFront else cameraSelectorBack)
        val viewFinder: PreviewView = requireView().findViewById(R.id.preview_view)
        preview.setSurfaceProvider(viewFinder.surfaceProvider)
        var camera = cameraProvider?.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
        viewFinder.setOnClickListener {
            Log.d(TAG, "bindPreview: onPreViewViewClick")
            cameraProvider?.unbindAll()
            initCamera(!front)
        }
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(1280,720))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext()),
            {
                imageProxy: ImageProxy ->
                val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                Log.d(TAG, "ImageAnalyzer,rotationDegrees$rotationDegrees")
                imageProxy.close()
            })
        cameraProvider?.bindToLifecycle(this as LifecycleOwner, cameraSelector, imageAnalysis)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    companion object {

        private const val TAG = "Fragment1"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}