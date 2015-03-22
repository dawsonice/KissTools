package com.kisstools.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import android.util.Base64;

public class VerifyUtil {

	private static final int BUFFER_SIZE = 8192;

	private String signKey;
	private String signAlgorithm;
	private String keyAlgorithm;
	private String signature;

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public void setSignAlgorithm(String sa) {
		this.signAlgorithm = sa;
	}

	public void setKeyAlgorithm(String ka) {
		this.keyAlgorithm = ka;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean verify(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (Exception e) {
			return false;
		}
		return verify(fis);
	}

	public boolean verify(InputStream is) {
		boolean verified = false;
		try {
			byte[] keyBytes = Base64.decode(signKey, Base64.NO_WRAP);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
			PublicKey publicKey = keyFactory.generatePublic(spec);

			Signature signer = Signature.getInstance(signAlgorithm);
			signer.initVerify(publicKey);

			byte[] buffer = new byte[BUFFER_SIZE];
			int count;
			while ((count = is.read(buffer)) > 0) {
				signer.update(buffer, 0, count);
			}
			is.close();

			byte[] signatureBytes = Base64.decode(signature, Base64.NO_WRAP);
			verified = signer.verify(signatureBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verified;
	}

}
