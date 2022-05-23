package com.hdb.hrsguard.abse.scheme;


import com.hdb.hrsguard.abse.FormatTransfor.ElementToBytes;
import com.hdb.hrsguard.abse.filepConstant.FileNameConstant;
import com.hdb.hrsguard.abse.filepConstant.SearchEncPath;
import com.hdb.hrsguard.entity.admin.WFIndex;
import com.hdb.hrsguard.searchEnc.FileUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.IOException;

public class Bswabe {
	public static void setup(String []u,BswabePub pub, BswabeMsk msk) throws IOException {
		
		System.out.println("---setup phase---");
		int n = u.length;
		Element a,b,c;
		msk.r = new Element[2*n];
		msk.x = new Element[2*n];
		pub.u = new Element[2*n];
		pub.y = new Element[2*n];

		pub.p=PairingFactory.getPairing(SearchEncPath.TypeAPath);
		Pairing pairing = pub.p;
		/*
		 * parring 这个接口提供了对函数的访问。配对包括阶为r的素数群，分别称为 G1、 G2和 GT。该配对是一个双线性映射，
		 * 以两个元素为输入，一个来自 G1，一个来自 G2，并输出一个 GT 元素。有时 g1和 g2是同一个群(即配对是对称的) ，
		 * 因此它们的元素可以自由地混合。*/

		//pub,随机产生一个G_p_1中的元素
		pub.g1 = pairing.getG1().newRandomElement();//Returns a new random element.
		pub.g_a = pairing.getG1().newRandomElement();
		pub.g_b = pairing.getG1().newRandomElement();
		pub.g_c = pairing.getG1().newRandomElement();
		pub.g2 = pairing.getG2().newRandomElement();
		pub.g2.set(pub.g1);//Sets this element to value.
		

		for(int i=0;i<2*u.length;i++){
			pub.y[i] = pairing.getGT().newElement();
			pub.u[i]=pairing.getG1().newElement();
		}
		//随机产生一个Zr group中的元素
		a= pairing.getZr().newElement();
		b= pairing.getZr().newElement();
		c= pairing.getZr().newElement();
		for(int i=0;i<2*u.length;i++){
			msk.r[i]=pairing.getZr().newRandomElement();
			msk.x[i]=pairing.getG2().newRandomElement();
		}
		//如果这个元素位于有限代数结构中，就给它分配一个均匀随机元素(Zr)
		msk.a = a.setToRandom();
		msk.b = b.setToRandom();
		msk.c = c.setToRandom();
		/*
		 * 其实为了保险起见，防止Element在运算的过程中修改了Element原本的数值，
		 * 可以使用Element.duplicate()方法。这个方法将返回一个与Element数值完全一样的Element，但是是个新的Element对象。*/
		//g_a power a(Zr)
		pub.g_a = pub.g1.duplicate();
		pub.g_a = pub.g_a.powZn(msk.a);
		
		pub.g_b = pub.g1.duplicate();
		pub.g_b = pub.g_b.powZn(msk.b);
		
		pub.g_c = pub.g1.duplicate();
		pub.g_c = pub.g_c.powZn(msk.c);
		
		Element r_neg;//= pairing.getZr().newElement();
		
		for(int i=0;i<2*u.length;i++){
			pub.y[i] = pairing.pairing(pub.g1, msk.x[i]);//y[i]=e(x_i,g1)
			r_neg = msk.r[i].duplicate();
			//Set this = -this.-r_i
			r_neg.negate();
			pub.u[i] = pub.g1.duplicate();
			pub.u[i]=pub.u[i].powZn(r_neg);//g1^(-r_i)
		}
		/*start to generate public keys and msk keys,msk keys are a must
		 * to generate further private keys */
		generatePublicKeys(pub,pairing);
		generateMskKeys(msk,pairing);

	}

	/**
	 * generate public keys
	 * @param pub
	 * @param pairing
	 */
	public static void generatePublicKeys(BswabePub pub, Pairing pairing) throws IOException {
		FileUtil.deleteFiles(SearchEncPath.PUBLIC_KEYS);
		ElementToBytes.writeElement(pub.g1,SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_g1,pairing);
		ElementToBytes.writeElement(pub.g_a,SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_g_a,pairing);
		ElementToBytes.writeElement(pub.g_b,SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_g_b,pairing);
		ElementToBytes.writeElement(pub.g_c,SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_g_c,pairing);
		ElementToBytes.writeElement(pub.g2,SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_g2,pairing);
		//Element[]
		ElementToBytes.writeElement(pub.u, SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_u,pairing);
		ElementToBytes.writeElement(pub.y, SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_y,pairing);
		System.out.println("public key has been generated successfully");
	}

	/**
	 * 从存储publickey的文件中读取公钥
	 * @param pub
	 * @param length
	 * @throws IOException
	 */
	public static void getFilePubKey(BswabePub pub,int length) throws IOException {
		pub.p=PairingFactory.getPairing(SearchEncPath.TypeAPath);
		Pairing pairing = pub.p;
		pub.g1=ElementToBytes.readElement(SearchEncPath.PUBLIC_KEYS+FileNameConstant.PUB_KEY_g1,pairing);
		pub.g_a=ElementToBytes.readElement(SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_g_a,pairing);
		pub.g_b=ElementToBytes.readElement(SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_g_b,pairing);
		pub.g_c=ElementToBytes.readElement(SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_g_c,pairing);
		pub.g2=ElementToBytes.readElement(SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_g2,pairing);
		//
		pub.u=ElementToBytes.readElement(SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_u,pairing,length);
		pub.y=ElementToBytes.readElement(SearchEncPath.PUBLIC_KEYS+ FileNameConstant.PUB_KEY_y,pairing,length);
	}

	/**
	 * msk keys are a must to generate further private keys
	 * @param msk
	 * @param pairing
	 */
	public static void generateMskKeys(BswabeMsk msk,Pairing pairing) throws IOException {
		FileUtil.deleteFiles(SearchEncPath.MSK_KEYS);
		ElementToBytes.writeElement(msk.a,SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_a,pairing);
		ElementToBytes.writeElement(msk.b,SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_b,pairing);
		ElementToBytes.writeElement(msk.c,SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_c,pairing);
		//Element []
		ElementToBytes.writeElement(msk.r,SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_r,pairing);
		ElementToBytes.writeElement(msk.x,SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_x,pairing);
		System.out.println("msk key has been generated successfully");
	}

	/**
	 * 从mskkey文件中读取msk密钥
	 * @param msk
	 * @param length
	 * @throws IOException
	 */
	public static void getFileMskKey(BswabeMsk msk,int length) throws IOException {
		Pairing pairing=PairingFactory.getPairing(SearchEncPath.TypeAPath);
		msk.a=ElementToBytes.readElement(SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_a,pairing);
		msk.b=ElementToBytes.readElement(SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_b,pairing);
		msk.c=ElementToBytes.readElement(SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_c,pairing);
		//Element []
		msk.r=ElementToBytes.readElement(SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_r,pairing,length);
		msk.x=ElementToBytes.readElement(SearchEncPath.MSK_KEYS+ FileNameConstant.MSK_KEY_x,pairing,length);

	}


	
	public static BswabeCph enc(String []u,BswabePub pub, String []policy,String word) throws Exception {
		BswabeCph cph = new BswabeCph();	
		Pairing pairing = pub.p;
		
		cph.w0 = pairing.getG1().newElement();
		cph.w = pairing.getG1().newElement();
		cph.u_gate = pairing.getG1().newElement();
		
		Element t1 = pairing.getZr().newRandomElement();
		Element t2 = pairing.getZr().newRandomElement();		
		Element m=pairing.getZr().newElement();//要加密的元素,将字符串映射到z上
		Element add = t1.duplicate();
		add.add(t2);//t1+t2
		Element w01 = pub.g_a.duplicate();
		w01.powZn(add);//g_a^(t1+t2)
		Element w02 = pub.g_b.duplicate();
		w02.powZn(t1);//g_b^t1
		
		//Index indTemp;
		byte []ind = word.getBytes();
		//将ind哈希到Z_r群
		m=m.setFromHash(ind, 0, ind.length);//H(w)

		
		cph.w0 = pub.g_c.duplicate();
		cph.w0.powZn(t1);//g^ct_1
		cph.w = w01.duplicate();
		cph.w.mul(w02.powZn(m));//g^a(t 1 +t 2 )g^bH(w)t1
		cph.u_gate = pub.g1.duplicate();
		cph.u_gate.powZn(t2);//u_gate=g1^t2
		
		
		for(int i=0;i<u.length;i++){
			if(isContain(policy,u[i])){
				cph.u_gate.mul(pub.u[i]);//u_gate=u_gate*pub.u[i]
			}
			else{
				cph.u_gate.mul(pub.u[i+u.length]);//u_gate=u_gate*pub.u[i+n]
			}
		}



		return cph;
	}

	/*
	 * Generate a private key with the given set of attributes.
	 */
	public static BswabePrv keygen(String []u,BswabePub pub, BswabeMsk msk, String[] attrs){
		int len = u.length;
		BswabePrv prv = new BswabePrv();
		Pairing pairing= pub.p;
		prv.sig = new Element[len];
		prv.y = new Element[len];
		
		
		prv.v = pairing.getG2().newElement();
		prv.sig_user = pairing.getG2().newElement();
		prv.y_user = pairing.getGT().newElement();
		for(int i=0;i<len;i++){
			prv.sig[i] = pairing.getG2().newElement();
			prv.y[i] = pairing.getGT().newElement();
		}
		
		prv.v = pub.g2.duplicate();	//g2=g1=g
		prv.v.powZn(msk.a);
		prv.v.powZn(msk.c);
		//v=g^ac
		for(int i=0;i<len;i++)
			prv.sig[i] = prv.v.duplicate();
		
		for(int i=0;i<len;i++){
			if(isContain(attrs,u[i])){
				prv.sig[i].powZn(msk.r[i]);//g^acr_i
				prv.sig[i].mul(msk.x[i]);//g^acr_i * x_i(即x_i*v^r_i)
				prv.y[i] = pub.y[i].duplicate();//y[i]=pub.y[i]
			}
			else{
				prv.sig[i].powZn(msk.r[i+len]);
				prv.sig[i].mul(msk.x[i+len]);//x_i*v^r_(i+n)
				prv.y[i] = pub.y[i+len].duplicate();//y[i]=pub.y[i+n]
			}
		}
		prv.sig_user = prv.sig[0].duplicate();
		prv.y_user = prv.y[0].duplicate();
		for(int i=1;i<len;i++){
			prv.sig_user.mul(prv.sig[i]);//δ_user
			prv.y_user.mul(prv.y[i]);//y_user
		}
		return prv;
		
	}
	
	public static boolean isContain(String []attrs,String u){
		boolean ret = false;
		for(int i=0;i<attrs.length;i++){
			if(u.equals(attrs[i])){
				ret = true;
				break;
			}
		}
		return ret;
	}

   
	
	
	
	public static BswabeToken tokgen(BswabePrv prv,BswabePub pub, String word){
		System.out.println("---begin token generation phase---");
		Pairing pairing = pub.p;
		BswabeToken token = new BswabeToken();
		token.tok1 = pairing.getG2().newElement();
		token.tok2 = pairing.getG2().newElement();
		token.tok3 = pairing.getG2().newElement();
		token.tok4 = pairing.getG2().newElement();
		token.tok5 = pairing.getGT().newElement();	
			
		Element s = pairing.getZr().newElement();
		s.setToRandom();
		Element wm = pairing.getZr().newElement();//要加密的关键字
		byte []w = word.getBytes();
		wm = wm.setFromHash(w, 0, w.length);
		
				
		token.tok1 = pub.g_b.duplicate();
		token.tok1.powZn(wm);//g^bH(w)
		token.tok1.mul(pub.g_a);//g^a * g^bH(w)
		token.tok1.powZn(s);//(g^a * g^bH(w))^s
		token.tok2 = pub.g_c.duplicate();
		token.tok2.powZn(s);//g^cs
		token.tok3 = prv.v.duplicate();
		token.tok3.powZn(s);//v^s=g^acs
		token.tok4 = prv.sig_user.duplicate();
		token.tok4.powZn(s);//δ_user^s
		token.tok5 = prv.y_user.duplicate();
		token.tok5.powZn(s);//y_user^s
		
		return token;
		
	}

	
	public static boolean search(BswabePub pub, BswabeToken token, BswabeCph cph) {
		System.out.println("---begin search generation phase---");
		boolean ret = false;		
		Pairing pairing = pub.p;
		Element E = pairing.getGT().newElement();
		E=pairing.pairing(cph.u_gate,token.tok3);//e(u_gate,v^s)
		E=E.mul(pairing.pairing(pub.g1, token.tok4));//e(u_gate,v^s)*e(δ_user^s,g)
		E=E.div(token.tok5);//e(u_gate,v^s)*e(δ_user^s,g) div y_user^s =e(g,g)^acst2
		
		Element left = pairing.getGT().newElement();
		Element right = pairing.getGT().newElement();
		
		left = pairing.pairing(cph.w0, token.tok1);	//e(g^ct1,(g^a * g^bH(w))^s)=e(g,g)^[ct1*(sa+bsh(w)]
		left = left.mul(E);
		//e(g^ct1,(g^a * g^bH(w))^s)  *  e(g,g)^acst2=e(g,g)^[acst2+ct1*(sa+bsh(w)]
		//=e(g,g)^[cs(at2+t1*(a+bH(w)]
		right = pairing.pairing(cph.w, token.tok2);
		// e(g^a(t 1 +t 2 )g^bH(w)t1,g^cs )=e(g,g)^[cs(at1+at2+t1*bH(w)]
		
		if(left.equals(right)){
			ret = true;			

		}			
				
		return ret;		
	}

	/**
	 * 创建一条关键词对应的索引
	 * @param cph
	 * @param filelist
	 * @return
	 */
	public static WFIndex createOneWFIndex(BswabeCph cph,String filelist,Long dep_id, Pairing pairing){
		WFIndex wfIndex=new WFIndex();
		//cph.w
		wfIndex.setW_group(pairing.getFieldIndex(cph.w.getField()));
		byte[]w=cph.w.toBytes();
		wfIndex.setW(w);
		wfIndex.setW_len(w.length);
		//cph.w0
		wfIndex.setW0_group(pairing.getFieldIndex(cph.w0.getField()));
		byte[]w0=cph.w0.toBytes();
		wfIndex.setW0(w0);
		wfIndex.setW0_len(w0.length);
		//cph.u_gate
		wfIndex.setU_gate_group(pairing.getFieldIndex(cph.u_gate.getField()));
		byte[]u_gate=cph.u_gate.toBytes();
		wfIndex.setU_gate(u_gate);
		wfIndex.setU_gate_len(u_gate.length);
		//filelist
		wfIndex.setFile_list(filelist);
		wfIndex.setDoc_dep_id(dep_id);
		return wfIndex;
	}

	/**
	 * 从数据库中读取一个关键词密文
	 * @param cph
	 * @param wfIndex
	 * @param pairing
	 */
	public static void getCphFromWFIndexDB(BswabeCph cph,WFIndex wfIndex,Pairing pairing){
		//cph.w
		int w_group=wfIndex.getW_group();
		int w_len=wfIndex.getW_len();
		byte[]w=new byte[w_len];
		w=wfIndex.getW();
		cph.w=pairing.getFieldAt(w_group).newElementFromBytes(w);
		//cph.w0
		int w0_group=wfIndex.getW0_group();
		int w0_len=wfIndex.getW0_len();
		byte[]w0=new byte[w0_len];
		w0=wfIndex.getW0();
		cph.w0=pairing.getFieldAt(w0_group).newElementFromBytes(w0);
		//cph.u_gate
		int u_gate_group=wfIndex.getU_gate_group();
		int u_gate_len=wfIndex.getU_gate_len();
		byte[]u_gate=new byte[u_gate_len];
		u_gate=wfIndex.getU_gate();
		cph.u_gate=pairing.getFieldAt(u_gate_group).newElementFromBytes(u_gate);
	}
	

}
