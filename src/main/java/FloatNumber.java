import Basic.Arithmetic;


public class FloatNumber extends AbstractALU {

    private String exponent1;
    private String exponent2;
    private String sig1;
    private String sig2;

    /**
     *
     * @param s1
     * @param s2
     * Given two binary 0/1 operand to generate suitable exponent and significand
     */
    private void initialize(String s1, String s2) {
        exponent1 = s1.substring(1, 9);
        String fractionSignificant = s1.substring(9);
        if (isAllZero(exponent1)) {
            sig1 = "0" + fractionSignificant;
            exponent1 = "00000001";
        } else {
            sig1 = "1" + fractionSignificant;
        }
        exponent2 = s2.substring(1, 9);
        fractionSignificant = s2.substring(9);
        if (isAllZero(exponent2)) {
            sig2 = "0" + fractionSignificant;
            exponent2 = "00000001";
        } else {
            sig2 = "1" + fractionSignificant;
        }
    }

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
        int decimalValue = 0;
        for (int i = 0; i < binaryExponent.length(); i++) {
            decimalValue  = decimalValue << 1;
            if (binaryExponent.charAt(i) == '1') decimalValue += 1;
        }
        decimalValue -= 127;
        return decimalValue;
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
//        String hexString = Float.toHexString(Float.parseFloat(n));
//        String sign = hexString.substring(0, 1);
//        String beforePoint = hexString.split("[.]")[0];
//        char isNormalized = beforePoint.charAt(beforePoint.length()-1);
//        String hexValue = hexString.split("[.]")[1];
//        String hexSignificant = hexValue.split("p")[0];
//        String decimalExponent = hexValue.split("p")[1];
//        if (isNormalized == '0') {            // 非规格化数存储时，要将指数存为-127
//            decimalExponent = "-127";
//        }
//        String binaryExponent = toBinaryExponent(decimalExponent);
//        String binarySignificant = toBinarySignificant(hexSignificant);
//        if (sign.equals("-")) sign = "1";
//        else sign = "0";
//        return sign + binaryExponent + binarySignificant;
        Float decimalValue = Float.parseFloat(n);
        int intBits = Float.floatToIntBits(decimalValue);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.insert(0, intBits & 1);
            intBits = intBits >> 1;
        }
        return sb.toString();
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

    protected String add(String s1, String s2) {
        if (toDecimal(s1).equals("0")) return s2;
        if (toDecimal(s2).equals("0")) return s1;
        initialize(s1, s2);

        if (!exponent1.equals(exponent2)) {        // 对齐指数
            int exponentValue1 = Integer.valueOf(exponent1, 2);
            int exponentValue2 = Integer.valueOf(exponent2, 2);
            int delta = Math.abs(exponentValue1 - exponentValue2);
            if (exponentValue1 > exponentValue2) {
                exponent2 = exponent1;
                sig2 = rightShift(sig2, delta, '0');
            } else {
                exponent1 = exponent2;
                sig1 = rightShift(sig1, delta, '0');
            }
        }
        if (isAllZero(sig1)) return s2;
        if (isAllZero(sig2)) return s1;


        String ansSignificant;                                          //   计算带符号数之和
        String ansExponent = exponent1;
        char ansSign = s1.charAt(0);
        if (s1.charAt(0) == s2.charAt(0)) {
            ansSignificant = unsignedAdd(sig1, sig2);
            if (adder.nextC == '1') {                      // 处理尾数相加溢出
                ansSignificant = rightShift(ansSignificant, 1, '1');
                adder.setOperand(ansExponent, repeat('0', ansExponent.length()));
                ansExponent = adder.calculate('1');
                if (isAllOne(ansExponent)) {               // 处理指数溢出
                    if (ansSign == '0') return toBinary("plus infinity");
                    else return toBinary("minus infinity");
                }
            }

        }
        else {
            ansSignificant = unsignedSub(sig1, sig2);
            if (isAllZero(ansSignificant)) return toBinary("0");    // 处理相减为0
            if (adder.nextC != '1') {
                ansSign = Arithmetic.NOT(ansSign);
                String reverse = reverse(ansSignificant);
                adder.setOperand(reverse, repeat('0', reverse.length()));
                ansSignificant = adder.calculate('1');
            }
        }

        //       Normalization  规格化
        int offset = ansSignificant.indexOf("1");
        for (int i = 0; i < offset; i++) {
            adder.setOperand(ansExponent, repeat('1', ansExponent.length()));
            ansExponent = adder.calculate('0');           // 指数减一
            if (isAllZero(ansExponent)) {
                break;
            }
            ansSignificant = leftShift(ansSignificant, 1, '0');
        }

        return ansSign + ansExponent + ansSignificant.substring(1);
    }

    private String unsignedAdd(String s1, String s2) {
        return adder.setOperand(s1, s2).calculate('0');
    }

    private String unsignedSub(String s1, String s2) {
        s2 = reverse(s2);
        return adder.setOperand(s1, s2).calculate('1');
    }

    protected String sub(String s1, String s2) {
        s2 = Arithmetic.NOT(s2.charAt(0)) + s2.substring(1);
        return add(s1, s2);
    }

    protected String multi(String s1, String s2) {
        if (toDecimal(s1).equals("0") || toDecimal(s2).equals("0")) return toBinary("0");
        initialize(s1, s2);

        int decimalExponent = Integer.valueOf(exponent1, 2) + Integer.valueOf(exponent2, 2) - 127*2;
        if (decimalExponent >= 128) {
            if (s1.charAt(0) == '1') return toBinary("minus infinity");
            else return toBinary("plus infinity");
        }

        int len = sig1.length();
        StringBuilder sb = new StringBuilder(repeat('0', len) + sig2);
        for (int i = 0; i < len; i++) {
            adder.nextC = '0';                     // 考虑加法可能产生进位
            String half = sb.substring(0, len);
            String newHalf;
            if (sb.charAt(sb.length() - 1) == '1') {
                newHalf = adder.setOperand(half, sig1).calculate('0');
            } else {
                newHalf = half;
            }
            sb.replace(0, len, newHalf);
            if (i != len - 1) {
                sb = rightShift(sb, 1, adder.nextC);
            }
        }
        if (adder.nextC == '1') {
            decimalExponent++;
            sb = rightShift(sb, 1, adder.nextC);
        }
        String ansSignificant = sb.substring(0, len);
        int offset = ansSignificant.indexOf("1");
        for (int i = 0; i < offset; i++) {
            decimalExponent--;
            if (decimalExponent == -127) {
                break;
            }
            ansSignificant = leftShift(ansSignificant, 1, '0');
        }
        char ansSign = Arithmetic.XOR(s1.charAt(0), s2.charAt(0));
        String ansExponent = toBinaryExponent(String.valueOf(decimalExponent));
        return ansSign + ansExponent + ansSignificant.substring(1);
    }

    public String[] division() {
        String[] ans = division(n1, n2);
        return new String[]{toDecimal(ans[0]), ""};
    }

    protected String[] division(String s1, String s2) {
        String[] ans = new String[2];
        ans[1] = "";
        if (toDecimal(s1).equals("0")) {
            ans[0] = toBinary("0");
            return ans;
        }
        if (toDecimal(s2).equals("0")) {
            if (s1.charAt(0) == '1') ans[0] = toBinary("minus infinity");
            else ans[0] = toBinary("plus infinity");
            return ans;
        }

        initialize(s1, s2);

        int decimalExponent = Integer.valueOf(exponent1, 2) - Integer.valueOf(exponent2, 2);
        if (decimalExponent >= 128) {
            if (s1.charAt(0) == '1') ans[0] = toBinary("minus infinity");
            else ans[0] = toBinary("plus infinity");
            return ans;
        }

        int len = sig1.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            adder.nextC = '0';
            String afterSub = unsignedSub(sig1, sig2);
            if (adder.nextC == '0') {      // 不够减
                sb.append('0');
            } else {
                sig1 = afterSub;
                sb.append('1');
            }
            sig2 = rightShift(sig2, 1, '0');        //   右移除数
        }

        String ansSignificant = sb.toString();
        int offset = ansSignificant.indexOf("1");
        for (int i = 0; i < offset && decimalExponent > -127; i++) {
            decimalExponent--;
            ansSignificant = leftShift(ansSignificant, 1, '0');
        }
        if (decimalExponent == -127) {
            ansSignificant = rightShift(ansSignificant, 1, '0');
        }

        char ansSign = Arithmetic.XOR(s1.charAt(0), s2.charAt(0));
        String ansExponent = toBinaryExponent(String.valueOf(decimalExponent));
        ans[0] = ansSign + ansExponent + ansSignificant.substring(1);
        return ans;
    }
}
