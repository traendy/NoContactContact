package de.traendy.nocontact.ui.qrcodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.traendy.database.model.QrCode
import de.traendy.featureflag.FeatureFlag
import de.traendy.featureflag.RuntimeBehavior
import de.traendy.nocontact.R
import de.traendy.nocontact.databinding.FragmentQrcodeListElementBinding
import de.traendy.qrcode.QrCodeGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val contentMinLines = 1
const val contentMaxLines = 100

class QrCodeRecyclerViewAdapter(private val deleteCallback: (QrCode) -> Unit, private val shareCallback: (QrCode) -> Unit) :
        ListAdapter<QrCode, QrCodeRecyclerViewAdapter.QrCodeViewHolder>(QrCodeDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addAndSubmitList(list: List<QrCode>?) {
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QrCodeViewHolder {
        return QrCodeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holderQrCode: QrCodeViewHolder, position: Int) {
        holderQrCode.bind(getItem(position), deleteCallback, shareCallback)
    }

    class QrCodeViewHolder(private val binding: FragmentQrcodeListElementBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): QrCodeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentQrcodeListElementBinding.inflate(layoutInflater, parent, false)

                return QrCodeViewHolder(
                        binding
                )
            }
        }

        fun bind(qrCode: QrCode, deleteCallback: (QrCode) -> Unit, shareCallback: (QrCode) -> Unit) {
            binding.sectionLabel.text = qrCode.title
            binding.imageView.setImageBitmap(
                    QrCodeGenerator().createQrCodeBitMap(
                            qrCode.content ?: "",
                            binding.root.context.resources.getDimensionPixelSize(R.dimen.barcode_image_size)
                    )
            )
            binding.deleteButton.setOnClickListener { deleteCallback(qrCode) }
            if (RuntimeBehavior.isFeatureEnabled(FeatureFlag.SHARE_CONTENT)) {
                binding.shareButton.setOnClickListener { shareCallback(qrCode) }
            } else {
                binding.shareButton.visibility = View.GONE
            }

            binding.contentData.text = qrCode.content
            binding.contentData.setOnClickListener {
                if (binding.contentData.maxLines == contentMinLines) {
                    binding.contentData.maxLines = contentMaxLines
                } else {
                    binding.contentData.maxLines = contentMinLines
                }

            }
        }
    }

    class QrCodeDiffCallback : DiffUtil.ItemCallback<QrCode>() {
        override fun areItemsTheSame(oldItem: QrCode, newItem: QrCode): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: QrCode, newItem: QrCode): Boolean {
            return oldItem == newItem
        }
    }
}