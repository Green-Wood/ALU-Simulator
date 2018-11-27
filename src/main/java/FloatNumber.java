import Basic.Arithmetic;
import Basic.StringGenerator;


public class FloatNumber extends ALU {

    protected String toDecimal(String bin) {
        String binaryExponent = bin.substring(1, 9);
        String binarySignificant = bin.substring(9, 32);
        if (isAllZero(binaryExponent) && isAllZero(binarySignificant)) {
            return "0";
        } else if (isAllOne(binaryExponent) && isAllZero(binarySignificant)) {
            if (bin.charAt(0) == '0') return "plus infinity";
            else return "minus infinity";
        } else if (isAllOne(binaryExponent) && !isAllZero(binarySignificant)) {
            return "NaN";
        } else if (isAllZero(binaryExponent) && !isAllZero(binarySignificant)){
            return readBinaryDenormalizd(bin);
        } else {
            return readBinaryNormalized(bin);
        }
    }

    private String readBinaryNormalized(String bin) {
        String binaryExponent = bin.substring(1, 9);
        String binarySignificant = bin.substring(9, 32);
        int decimalExponent = readBinaryExponent(binaryExponent);
        float decimalSignificant = readBinarySignificant(binarySignificant, true);
        float decimalValue = decimalSignificant * (float) Math.pow(2, decimalExponent);
        if (bin.charAt(0) == '1') decimalValue *= -1;
        return String.valueOf(decimalValue);
    }

    private String readBinaryDenormalizd(String bin) {
        String binarySignificant = bin.substring(9, 32);
        float decimalSignificant = readBinarySignificant(binarySignificant, false);
        float decimalValue = decimalSignificant * (float) Math.pow(2, -126);     // 非规格数直接乘以2的-126次方
        if (bin.charAt(0) == '1') decimalValue *= -1;
        return String.valueOf(decimalValue);
    }

    private int readBinaryExponent(String binaryExponent) {
        int decimalvalue = 0;
        for (int i = 0; i < binaryExponent.length(); i++) {
            decimalvalue  = decimalvalue << 1;
            if (binaryExponent.charAt(i) == '1') decimalvalue += 1;
        }
        decimalvalue -= 127;
        return decimalvalue;
    }

    private float readBinarySignificant(String binarySignificant, boolean isNormalized) {
        float decimalValue = 0;
        if (isNormalized) {
            decimalValue += 1;
        }
        double fractionValue = 0;
        for (int i = binarySignificant.length() - 1; i >= 0; i--) {
            fractionValue  *= 0.5;
            if (binarySignificant.charAt(i) == '1') fractionValue += 0.5;
        }
        decimalValue += fractionValue;
        return decimalValue;
    }

    private boolean isAllOne(String bin) {
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) != '1') return false;
        }
        return true;
    }

    private boolean isAllZero(String bin) {
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) != '0') return false;
        }
        return true;
    }

    protected String toBinary(String n) {
        if (n.equals("NaN")) {
            return "01111111100000000000000000000001";
        } else if (n.equals("plus infinity")) {
            return "01111111100000000000000000000000";
        } else if (n.equals("minus infinity")) {
            return "11111111100000000000000000000000";
        } else {
            return toBinaryWithActualValue(n);
        }
    }

    private String toBinaryWithActualValue(String n) {
        String hexString = Float.toHexString(Float.parseFloat(n));
        String sign = hexString.substring(0, 1);
        String beforePoint = hexString.split("[.]")[0];
        char isNormalized = beforePoint.charAt(beforePoint.length()-1);
        String hexValue = hexString.split("[.]")[1];
        String hexSignificant = hexValue.split("p")[0];
        String decimalExponent = hexValue.split("p")[1];
        if (isNormalized == '0') {            // 非规格化数存储时，要将指数存为-127
            decimalExponent = "-127";
        }
        String binaryExponent = toBinaryExponent(decimalExponent);
        String binarySignificant = toBinarySignificant(hexSignificant);
        if (sign.equals("-")) sign = "1";
        else sign = "0";
        return sign + binaryExponent + binarySignificant;
    }

    /**
     *
     * @param decimalExponent 十进制的指数
     * @return 返回一个8位长度的二进制指数
     */
    private String toBinaryExponent(String decimalExponent) {
        int decimalValue = Integer.parseInt(decimalExponent) + 127;
        String binaryExponent = Integer.toBinaryString(decimalValue);
        while (binaryExponent.length() != 8){
            binaryExponent = "0" + binaryExponent;
        }
        return binaryExponent;
    }

    /**
     *
     * @param hexExponent 十六进制的尾数
     * @return 返回一个23位长度的二进制尾数
     */
    private String toBinarySignificant(String hexExponent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hexExponent.length(); i++) {
            String currBin = getBinaryThroughHex(hexExponent.substring(i, i+1));
            sb.append(currBin);
        }
        while (sb.length() != 24) {
            sb.append("0000");
        }
        return sb.deleteCharAt(23).toString();
    }

    /**
     *
     * @param hex 一个十六进制数
     * @return  返回一个长度为4位的二进制数
     */
    private String getBinaryThroughHex(String hex) {
        int decimalValue = Integer.valueOf(hex, 16);
        String bin = Integer.toBinaryString(decimalValue);
        while (bin.length() != 4) {
            bin = '0' + bin;
        }
        return bin;
    }

    protected String add(String s1, String s2) {
        if (toDecimal(s1).equals("0")) return s2;
        if (toDecimal(s2).equals("0")) return s1;
        String exponent1 = s1.substring(1, 9);
        String sig1 = s1.substring(9);
        if (isAllZero(exponent1)) {
            sig1 = "0" + sig1;
            exponent1 = "00000001";
        }
        else sig1 = "1" + sig1;
        String exponent2 = s2.substring(1, 9);
        String sig2 = s2.substring(9);
        if (isAllZero(exponent2)) {
            sig2 = "0" + sig2;
            exponent2 = "00000001";
        }
        else sig2 = "1" +sig2;


        if (!exponent1.equals(exponent2)) {        // 对齐指数
            int exponentValue1 = Integer.valueOf(exponent1, 2);
            int exponentValue2 = Integer.valueOf(exponent2, 2);
            int delta = Math.abs(exponentValue1 - exponentValue2);
            if (exponentValue1 > exponentValue2) {
                exponent2 = exponent1;
                sig2 = StringGenerator.repeat('0', delta) + sig2.substring(0, sig2.length()-delta);
            } else {
                exponent1 = exponent2;
                sig1 = StringGenerator.repeat('0', delta) + sig1.substring(0, sig1.length()-delta);
            }
        }
        if (isAllZero(sig1)) return s2;
        if (isAllZero(sig2)) return s1;


        String ansSignificant;                                          //   计算带符号数之和
        String ansExponent = exponent1;
        String ansSign = s1.substring(0, 1);
        if (s1.charAt(0) == s2.charAt(0)) {
            ansSignificant = unsignedAdd(sig1, sig2);
            if (adder.nextC == '1') {                      // 处理尾数相加溢出
                ansSignificant = "1" + ansSignificant.substring(0, ansSignificant.length()-1);
                adder.setOperand(ansExponent, StringGenerator.repeat('0', ansExponent.length()));
                ansExponent = adder.calculate('1');
                if (isAllZero(ansExponent)) {               // 处理指数溢出
                    if (ansSign.equals("0")) return toBinary("plus infinity");
                    else return toBinary("minus infinity");
                }
            }

        }
        else {
            ansSignificant = unsignedSub(sig1, sig2);
            if (isAllZero(ansSignificant)) return toBinary("0");    // 处理相减为0
            if (adder.nextC != '1') {
                if (ansSign.equals("1")) ansSign = "0";
                else ansSign = "1";
                String reverse = StringGenerator.getReverse(ansSignificant);
                adder.setOperand(reverse, StringGenerator.repeat('0', reverse.length()));
                ansSignificant = adder.calculate('1');
            }
            int offset = ansSignificant.indexOf("1");
            for (int i = 0; i < offset; i++) {
                adder.setOperand(ansExponent, StringGenerator.repeat('1', ansExponent.length()));
                ansExponent = adder.calculate('0');
                if (isAllZero(ansExponent)) {
                    break;
                }
                ansSignificant = ansSignificant.substring(1) + "0";
            }
        }

        return ansSign + ansExponent + ansSignificant.substring(1);
    }

    private String unsignedAdd(String s1, String s2) {
        adder.setOperand(s1, s2);
        return adder.calculate('0');
    }

    private String unsignedSub(String s1, String s2) {
        s2 = StringGenerator.getReverse(s2);
        adder.setOperand(s1, s2);
        return adder.calculate('1');
    }

    protected String sub(String s1, String s2) {
        if (s2.charAt(0) == '1') s2 = "0" + s2.substring(1);
        else s2 = "1" + s2.substring(1);
        return add(s1, s2);
    }

    protected String multi(String s1, String s2) {
        if (toDecimal(s1).equals("0") || toDecimal(s2).equals("0")) return toBinary("0");
        String exponent1 = s1.substring(1, 9);
        String sig1 = s1.substring(9);
        if (isAllZero(exponent1)) {
            sig1 = "0" + sig1;
            exponent1 = "00000001";
        }
        else sig1 = "1" + sig1;
        String exponent2 = s2.substring(1, 9);
        String sig2 = s2.substring(9);
        if (isAllZero(exponent2)) {
            sig2 = "0" + sig2;
            exponent2 = "00000001";
        }
        else sig2 = "1" +sig2;

        int decimalExponent = Integer.valueOf(exponent1, 2) + Integer.valueOf(exponent2, 2) - 127*2;
        if (decimalExponent >= 128) {
            if (s1.charAt(0) == '1') return toBinary("minus infinity");
            else return toBinary("plus infinity");
        }

        int len = sig1.length();
        StringBuilder sb = new StringBuilder(StringGenerator.repeat('0', len) + sig2);
        for (int i = sig2.length() - 1; i >= 0; i--) {
            adder.nextC = '0';                     // 考虑加法可能产生进位
            String half = sb.substring(0, len);
            String newHalf;
            if (sig2.charAt(i) == '1') {
                adder.setOperand(half, sig1);
                newHalf = adder.calculate('0');
            } else {
                newHalf = half;
            }
            sb.replace(0, len, newHalf);
            sb.deleteCharAt(sb.length()-1);
            sb.insert(0, adder.nextC);
        }
        String ansSignificant = sb.substring(0, len);
        int offset = ansSignificant.indexOf("1");
        decimalExponent++;
        for (int i = 0; i < offset; i++) {
            decimalExponent--;
            if (decimalExponent == -127) {
                break;
            }
            ansSignificant = ansSignificant.substring(1) + "0";
        }
        String ansSign = String.valueOf(Arithmetic.XOR(s1.charAt(0), s2.charAt(0)));
        String ansExponent = toBinaryExponent(String.valueOf(decimalExponent));
        return ansSign + ansExponent + ansSignificant.substring(1);
    }

    protected String division(String s1, String s2) {
        if (toDecimal(s1).equals("0")) return toBinary("0");
        if (toDecimal(s2).equals("0")) {
            if (s1.charAt(0) == '1') return toBinary("minus infinity");
            else return toBinary("plus infinity");
        }

        String exponent1 = s1.substring(1, 9);
        String sig1 = s1.substring(9);
        if (isAllZero(exponent1)) {
            sig1 = "0" + sig1;
            exponent1 = "00000001";
        }
        else sig1 = "1" + sig1;
        String exponent2 = s2.substring(1, 9);
        String sig2 = s2.substring(9);
        if (isAllZero(exponent2)) {
            sig2 = "0" + sig2;
            exponent2 = "00000001";
        }
        else sig2 = "1" +sig2;

        int decimalExponent = Integer.valueOf(exponent1, 2) - Integer.valueOf(exponent2, 2);
        if (decimalExponent >= 128) {
            if (s1.charAt(0) == '1') return toBinary("minus infinity");
            else return toBinary("plus infinity");
        }

        int len = sig1.length();
        StringBuilder sb = new StringBuilder(sig1 + StringGenerator.repeat('0', len));
        ALU integer = new IntegerNumber();
        for (int i = 0; i < len; i++) {
            adder.nextC = '0';
            String half = sb.substring(0, len);
            String newHalf = integer.sub(half, sig2);
            if (adder.nextC == '0') {      // 不够减
                newHalf = half;
            }
            sb.replace(0, len, newHalf);
            sb.deleteCharAt(0);
            sb.append(adder.nextC);
        }

        String ansSignificant = sb.substring(len);
        int offset = ansSignificant.indexOf("1");
        for (int i = 0; i < offset; i++) {
            decimalExponent--;
            if (decimalExponent == -127) {
                break;
            }
            ansSignificant = ansSignificant.substring(1) + "0";
        }

        String ansSign = String.valueOf(Arithmetic.XOR(s1.charAt(0), s2.charAt(0)));
        String ansExponent = toBinaryExponent(String.valueOf(decimalExponent));
        return ansSign + ansExponent + ansSignificant.substring(1);
    }
}
