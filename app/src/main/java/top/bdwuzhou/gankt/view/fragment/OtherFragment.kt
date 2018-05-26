package top.bdwuzhou.gankt.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import okhttp3.*
import okio.ByteString
import top.bdwuzhou.gankt.R
import top.bdwuzhou.gankt.util.findViewById
import top.bdwuzhou.gankt.view.fragment.callback.OnFragmentInteractionListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OtherFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mTvContent: TextView
    private lateinit var mBtnSend: Button
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var socket: WebSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_other, container, false)
        mBtnSend = view findViewById R.id.btn_click
        mTvContent = view findViewById R.id.tv_content
        init()
        return view
    }

    private fun init() {
        mBtnSend.setOnClickListener({ socket.send("test") })
        val client: OkHttpClient = OkHttpClient.Builder().build()
        val request = Request.Builder()
                .url("ws://192.168.1.69:5555")
                .build()
        socket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket?, response: Response?) {
                super.onOpen(webSocket, response)
                mTvContent.post({
                    mTvContent.append("socket open\n")
                })
            }

            override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
                super.onFailure(webSocket, t, response)
                mTvContent.post({
                    mTvContent.append("socket failure:\n${t?.cause}")
                })
            }

            override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
                super.onClosing(webSocket, code, reason)
                mTvContent.post({
                    mTvContent.append("socket closing\n")
                })
            }

            override fun onMessage(webSocket: WebSocket?, text: String?) {
                super.onMessage(webSocket, text)
                mTvContent.post({
                    mTvContent.append("socket string message:\n$text\n")
                })
            }

            override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
                super.onMessage(webSocket, bytes)
                mTvContent.post({
                    mTvContent.append("socket bytes message:\n${bytes.toString()}\n")
                })
            }

            override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
                super.onClosed(webSocket, code, reason)
                mTvContent.post({
                    mTvContent.append("socket closed\n")
                })
            }
        })
//        client.dispatcher().executorService().shutdown()
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                OtherFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
