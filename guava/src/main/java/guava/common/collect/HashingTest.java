package guava.common.collect;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HashingTest {

    @Test
    public void test(){
        String text = "hello world";
        String key = "Double123";

        //MD5
        log.info("md5:{}", Hashing.md5().hashBytes(text.getBytes()).toString());
        log.info("md5:{}",Hashing.hmacMd5(key.getBytes()).hashBytes(text.getBytes()).toString());

        //sha256
        log.info("sha256:{}",Hashing.sha256().hashBytes(text.getBytes()).toString());
        log.info("sha256:{}",Hashing.hmacSha256(key.getBytes()).hashBytes(text.getBytes()).toString());

        //sha512
        log.info("sha512:{}",Hashing.sha512().hashBytes(text.getBytes()).toString());
        log.info("sha512:{}",Hashing.hmacSha512(key.getBytes()).hashBytes(text.getBytes()).toString());

        //crc32
        log.info("crc32:{}",Hashing.crc32().hashBytes(text.getBytes()).toString());
        log.info("crc32c:{}",Hashing.crc32c().hashBytes(text.getBytes()).toString());

        log.info("md5:{}",Hashing.md5().hashUnencodedChars(text).toString());  //md5摘要，不执行字符编码
        log.info("md5:{}",Hashing.md5().newHasher().putUnencodedChars(text).hash().toString());

    }
    /**
     * 10:35:43.599 [main] INFO guava.common.collect.HashingTest - md5:5eb63bbbe01eeed093cb22bb8f5acdc3
     * 10:35:43.916 [main] INFO guava.common.collect.HashingTest - md5:bc537ab27b055704bc5591c5b9eff3d2
     * 10:35:43.916 [main] INFO guava.common.collect.HashingTest - sha256:b94d27b9934d3e08a52e52d7da7dabfac484efe37a5380ee9088f7ace2efcde9
     * 10:35:43.917 [main] INFO guava.common.collect.HashingTest - sha256:91d8c1d4d92003f036cff1a5222f8dddf9cd042fa96348ce9e2e863a91098b4d
     * 10:35:43.917 [main] INFO guava.common.collect.HashingTest - sha512:309ecc489c12d6eb4cc40f50c902f2b4d0ed77ee511a7c7a9bcd3ca86d4cd86f989dd35bc5ff499670da34255b45b0cfd830e81f605dcf7dc5542e93ae9cd76f
     * 10:35:43.918 [main] INFO guava.common.collect.HashingTest - sha512:4ffb60af390afef18ec5ee1b9dd518f3b36d328dd8a0dcb057db9b65238e7c741f895781fa0b15c2657943c0e711038899878cf462a01cfa3e1e5c7087a78708
     * 10:35:43.921 [main] INFO guava.common.collect.HashingTest - crc32:85114a0d
     * 10:35:43.921 [main] INFO guava.common.collect.HashingTest - crc32:aa6594c9
     * 10:35:43.922 [main] INFO guava.common.collect.HashingTest - md5:e42b054623b3799cb71f0883900f2764
     * 10:35:43.922 [main] INFO guava.common.collect.HashingTest - md5:e42b054623b3799cb71f0883900f2764
     */
}
