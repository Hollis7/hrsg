package com.hdb.hrsguard.abse.FormatTransfor;


import com.hdb.hrsguard.abse.filepConstant.SufixConstant;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement;

import java.io.*;

/**
 * @Description 将Element转化为bytes写入文件
 * @Author hollis
 */
public class ElementToBytes {
    public  static void writeElement (Element elem, String filename, Pairing pairing) throws IOException{
        DataOutputStream dOut = new DataOutputStream(new FileOutputStream(filename+ SufixConstant.DAT));
        dOut.writeBoolean(elem == null);
        if (elem == null) {
            return;
        }
        dOut.writeInt(pairing.getFieldIndex(elem.getField()));
        byte[] bytes = elem.toBytes();
        dOut.writeInt(bytes.length);
        dOut.write(bytes);
        dOut.writeBoolean(elem instanceof CurveElement && elem.isZero());
        if (elem instanceof CurveElement && elem.isZero()) {
            throw new IOException("Infinite element detected. They should not happen.");
        }
    }
    public  static void writeElement (Element []elem, String filename, Pairing pairing) throws IOException{
        DataOutputStream dOut = new DataOutputStream(new FileOutputStream(filename+ SufixConstant.DAT));
        dOut.writeBoolean(elem == null);
        if (elem == null) {
            return;
        }
        int elem_length=elem.length;
        for(int i=0;i<elem_length;i++){
            dOut.writeInt(pairing.getFieldIndex(elem[i].getField()));
            byte[] bytes = elem[i].toBytes();
            dOut.writeInt(bytes.length);
            dOut.write(bytes);
            dOut.writeBoolean(elem[i] instanceof CurveElement && elem[i].isZero());
            if (elem[i] instanceof CurveElement && elem[i].isZero()) {
                throw new IOException("Infinite element detected. They should not happen.");
            }
        }
    }
    public static Element readElement(String filename, Pairing pairing) throws IOException {
        DataInputStream dIn = new DataInputStream(new FileInputStream(filename+ SufixConstant.DAT));
        if (dIn.readBoolean()) {
            return null;
        }
        int fieldIndex = dIn.readInt(); // TODO: check if this is in a sensible range
        int length = dIn.readInt(); // TODO: check if this is in a sensible range
        byte[] bytes = new byte[length];
        dIn.readFully(bytes); // throws an exception if there is a premature EOF
        Element e = pairing.getFieldAt(fieldIndex).newElementFromBytes(bytes);        // this is a workaround because it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement does not serialize the infFlag
        boolean instOfCurveElementAndInf = dIn.readBoolean();
        if (instOfCurveElementAndInf) {
            //e.setToZero(); // according to the code this simply sets the infFlag to 1
            throw new IOException("The point is infinite. This shouldn't happen.");
        }
        return e;
    }
    public static Element []readElement(String filename, Pairing pairing,int elem_length) throws IOException {
        DataInputStream dIn = new DataInputStream(new FileInputStream(filename+ SufixConstant.DAT));
        if (dIn.readBoolean()) {
            return null;
        }
        Element []e=new Element[elem_length];
        for(int i=0;i<elem_length;i++){
        int fieldIndex = dIn.readInt(); // TODO: check if this is in a sensible range
        int length = dIn.readInt(); // TODO: check if this is in a sensible range
        byte[] bytes = new byte[length];
        dIn.readFully(bytes); // throws an exception if there is a premature EOF
        e[i]= pairing.getFieldAt(fieldIndex).newElementFromBytes(bytes);        // this is a workaround because it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement does not serialize the infFlag
        boolean instOfCurveElementAndInf = dIn.readBoolean();
        if (instOfCurveElementAndInf) {
            //e.setToZero(); // according to the code this simply sets the infFlag to 1
            throw new IOException("The point is infinite. This shouldn't happen.");
        }
        }
        return e;
    }

}
