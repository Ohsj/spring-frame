package kr.co.osj4532.util

import com.lowagie.text.DocumentException
import com.lowagie.text.pdf.PdfCopy
import com.lowagie.text.pdf.PdfImportedPage
import com.lowagie.text.pdf.PdfReader
import kr.co.osj4532.kr.co.osj4532.util.DateUtils
import kr.co.osj4532.part.dto.GetPdfUtilOut
import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.BufferedOutputStream
import java.io.File
import java.io.IOException
import java.util.*

/**
 * 201210 | osj4532 | created
 * 201213 | osj4532 | delete temp file function add
 */
@Component
class PdfUtils {
    companion object {
        @Throws(IOException::class)
        fun generatePdf(htmlPath: String): ByteArray? {
            val result = makePdfOne(ResourceUtils.getFile(htmlPath))
            if (result != null) {
                deleteFile(result.fileName)
                return result.pdf
            }
            return null
        }

        @Throws(IOException::class)
        fun generatePdf(htmlFile: File): ByteArray? {
            val result = makePdfOne(htmlFile)
            if (result != null) {
                deleteFile(result.fileName)
                return result.pdf
            }
            return null
        }
        @Throws(IOException::class)
        fun generateMergePdf(byteArrayList: List<ByteArray?>): ByteArray? {
            val result = mergePdf(byteArrayList)
            if (result != null) {
                deleteFile(result.fileName)
                return result.pdf
            }
            return null
        }

        @Throws(IOException::class)
        private fun makePdfOne(htmlFile: File): GetPdfUtilOut? {
            val htmlString = Jsoup.parse(htmlFile, "UTF-8").html()
            val xhtml = htmlToXhtml(htmlString)
            return if (xhtml.isNotEmpty()) xhtmlToPdf(xhtml)
                   else null
        }

        @Throws(DocumentException::class, IOException::class)
        fun mergePdf(byteArrayList: List<ByteArray?>): GetPdfUtilOut? {
            val fileName = "${DateUtils.getCurrentDate()}-mergePdf"
            val file = File(fileName)
            val doc = com.lowagie.text.Document()
            val copy = PdfCopy(doc, file.outputStream())
            doc.open()

            byteArrayList.forEach {
                if (it != null) {
                    val pdfReader = PdfReader(it)

                    val pageNum = pdfReader.numberOfPages
                    var page: PdfImportedPage

                    for (i in 1..pageNum) {
                        page = copy.getImportedPage(pdfReader, i)
                        copy.addPage(page)
                    }
                    copy.freeReader(pdfReader)
                }
            }
            copy.close()
            doc.close()

            return if (file.isFile && file.canRead() && file.length() > 0)
                    GetPdfUtilOut(
                        fileName = fileName,
                        pdf = FileUtils.readFileToByteArray(file)
                    )
                   else null
        }

        @Throws(IOException::class)
        private fun htmlToXhtml(html: String): String {
            val document = Jsoup.parse(html)
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml)
            return document.html()
        }

        @Throws(IOException::class)
        private fun xhtmlToPdf(xhtml: String): GetPdfUtilOut? {
            val fileName = "${DateUtils.getCurrentDate()}-pdf"
            val file = File(fileName)
            val renderer = ITextRenderer()
            renderer.setDocumentFromString(xhtml)
            renderer.layout()

            val os = BufferedOutputStream(file.outputStream())
            renderer.createPDF(os)

            return if (file.isFile && file.canRead() && file.length() > 0)
                    GetPdfUtilOut(
                        fileName = fileName,
                        pdf = FileUtils.readFileToByteArray(file)
                    )
                   else null
        }

        @Throws(IOException::class)
        private fun deleteFile(fileName: String?) {
            if (!fileName.isNullOrEmpty()) {
                val file = File(fileName)
                if (file.isFile && file.canRead() && file.exists()) {
                    file.delete()
                }
            }
        }
    }
}