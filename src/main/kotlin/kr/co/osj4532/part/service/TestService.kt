package kr.co.osj4532.part.service


import kr.co.osj4532.fwk.base.BaseService
import kr.co.osj4532.model.entity.QTestMst
import kr.co.osj4532.model.entity.TestMst
import kr.co.osj4532.part.dto.GetTestOut
import kr.co.osj4532.repo.jpa.TestMstRepo
import kr.co.osj4532.repo.mybatis.TestMapper
import kr.co.osj4532.repo.mybatis.TestMapper2
import kr.co.osj4532.repo.querydsl.QTestMstRepo
import kr.co.osj4532.util.DownloadUtils
import kr.co.osj4532.util.PdfUtils
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * 201201 | osj4532 | created
 */

@Service
class TestService(
        val mapper: TestMapper,
        val mapper2: TestMapper2,
        val testRepo: TestMstRepo,
        val qTestMstRepo: QTestMstRepo
): BaseService() {

    val htmlFilePath = "classpath:pdf/test.html"

    fun mapperTest(): List<GetTestOut> {
        return mapper.selectTest()
    }

    fun mapperTest2(): List<GetTestOut> {
        return mapper2.selectTest()
    }

    fun jpaTest(): List<TestMst>? {
        return testRepo.findAll()
    }

    fun queryDslTest(): List<TestMst>? {
        return qTestMstRepo.selectAllTest()
    }

    fun getPdfTest(): ResponseEntity<Resource>? {
        val byteArray = PdfUtils.makePdfOne(htmlFilePath)
        return if (byteArray != null) DownloadUtils.download(byteArray, "test.pdf")
               else null
    }

    fun getPdfMergeTest() : ResponseEntity<Resource>? {
        val list = mutableListOf<ByteArray?>()
        for (i in 0..2) {
            list.add(PdfUtils.makePdfOne(htmlFilePath))
        }
        val mergedPdf = PdfUtils.mergePdfToByteArray(list)
        return if (mergedPdf != null) DownloadUtils.download(mergedPdf, "merge.pdf")
               else null
    }
}