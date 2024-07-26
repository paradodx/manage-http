package org.example.managehttp.utils;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.crypto.InvalidCipherTextException;

import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SM2Utils {
    public SM2Utils() {
    }
    static {
        // 注册Bouncy Castle提供者
        Security.addProvider(new BouncyCastleProvider());
    }

    // 从证书获取公钥
    public static byte[] returnPublicKey_Q(String chainCert) throws CertificateException, NoSuchProviderException, IOException {
        ByteArrayInputStream bIn = new ByteArrayInputStream(formatB64Cert(chainCert).getBytes(StandardCharsets.UTF_8));
        // 创建certificateFactory实例, 用于解析X.509证书
        CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
        // 从输入流中生成证书
        X509Certificate cert0 = (X509Certificate) cf.generateCertificate(bIn);
        // 证书解析成ASN.1结构
        ASN1InputStream aIn = new ASN1InputStream(cert0.getEncoded());
        ASN1Sequence seq = (ASN1Sequence) aIn.readObject();
        // 解析获得公钥数据
        X509CertificateStructure cert = new X509CertificateStructure(seq);
        SubjectPublicKeyInfo subjectPublicKeyInfo = cert.getSubjectPublicKeyInfo();
        ASN1BitString publicKeyData = subjectPublicKeyInfo.getPublicKeyData();
        // 提取公钥数据
        byte[] encodedPublicKey = publicKeyData.getEncoded();
        byte[] eP = new byte[64];
        System.arraycopy(encodedPublicKey, 4, eP, 0, eP.length);
        bIn.close();
        aIn.close();
        return eP;
    }

    public static String formatB64Cert(String b64) {
        b64 = formatToB64Data(b64);
        StringBuilder cert = new StringBuilder("-----BEGIN CERTIFICATE-----\n");
        int size;
        if (b64.length() % 64 == 0) {
            size = b64.length() / 64;
        } else {
            size = b64.length() / 64 + 1;
        }

        for (int i = 0; i < size; ++i) {
            if (i < size - 1) {
                cert.append(b64, i * 64, (i + 1) * 64).append("\n");
            } else {
                cert.append(b64.substring(i * 64)).append("\n");
            }
        }

        cert.append("-----END CERTIFICATE-----");
        return cert.toString();
    }

    public static String formatToB64Data(String certStr) {
        certStr = certStr.replaceAll(" ", "");
        certStr = certStr.replaceAll("\n", "");
        certStr = certStr.replaceAll("\r", "");
        certStr = certStr.replaceAll("-----BEGINCERTIFICATE-----", "");
        certStr = certStr.replaceAll("-----ENDCERTIFICATE-----", "");
        return certStr;
    }
}
