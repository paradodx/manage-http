package org.example.managehttp.utils;


import org.springframework.stereotype.Component;

public class TestCert {
    private TestCert(){

    }
    public static String getBase64Cert(int index) {
        switch (index) {
            case 0:
                return AnHui.base64Cert;
            case 1:
                return ChuZhou.base64Cert;
            case 2:
                return AnQin.base64Cert;
            case 3:
                return FuYang.base64Cert;
            case 4:
                return ChiZhou.base64Cert;
            case 5:
                return HuangShan.base64Cert;
            case 6:
                return SuZhou.base64Cert;
            case 7:
                return TongLing.base64Cert;
            default:
                throw new RuntimeException("index out of bound");
        }
    }

    public static String getSm2PrivateKey(int index) {
        switch (index) {
            case 0:
                return AnHui.sm2PrivateKey;
            case 1:
                return ChuZhou.sm2PrivateKey;
            case 2:
                return AnQin.sm2PrivateKey;
            case 3:
                return FuYang.sm2PrivateKey;
            case 4:
                return ChiZhou.sm2PrivateKey;
            case 5:
                return HuangShan.sm2PrivateKey;
            case 6:
                return SuZhou.sm2PrivateKey;
            case 7:
                return TongLing.sm2PrivateKey;
            default:
                throw new RuntimeException("index out of bound");
        }
    }

    // 安徽省
    public static class AnHui {
        private AnHui() {
        }

        public static String sm2PrivateKey = "e46288df17f458919cb47aad53c15f9db9f6a377511ba3b95dea38db8cc16144";
        public static String base64Cert = "MIIBgjCCASWgAwIBAgIMSAYAAAABF1q5XzZ/MAwGCCqBHM9VAYN1BQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAMMCXRlc3RjYXNtMjAeFw0yMzA4MjkwOTA2MDVaFw0yNDA4MjkwOTA2MDVaMB4xCzAJBgNVBAYTAkNOMQ8wDQYDVQQDDAblronlvr0wWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAASQkFTlvg3ELBqAHOMMA79ur5puxwl/oYGpB5xTA5yqVZJQTniWhPctLSyuG2zIickYo0opkzCbT+cL6NNw4on6o0QwQjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDgYDVR0PAQH/BAQDAgDAMBEGCWCGSAGG+EIBAQQEAwIAgDAMBggqgRzPVQGDdQUAA0kAMEYCIQDA3VGVZxMSkIGqzFkPrvJpRXCrLd6X5PzTJ9D83W3dyQIhAN9hgPK1dmITM6nbepFumw+tN6qxtgfMbwGIc6haX3DM";
    }

    // 滁州市
    public static class ChuZhou {
        private ChuZhou() {
        }

        public static String sm2PrivateKey = "020e25661d89ab8c12c03e4b37ea9c84a6e2156e540134b6a4409775f5bd5253";
        public static String base64Cert = "MIIBgjCCASWgAwIBAgIMSAYAAAABF2CWf68VMAwGCCqBHM9VAYN1BQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAMMCXRlc3RjYXNtMjAeFw0yMzA4MjkwOTA2MDVaFw0yNDA4MjkwOTA2MDVaMB4xCzAJBgNVBAYTAkNOMQ8wDQYDVQQDDAbmu4Hlt54wWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAARmTQA+WxwjM27xjvf7HDuFGhIJ9KUA5ETMkzWyMhC+McZZKSrxsKpnfIyZkd2uNGLhjLHwxrHVxplL13VwXNogo0QwQjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDgYDVR0PAQH/BAQDAgDAMBEGCWCGSAGG+EIBAQQEAwIAgDAMBggqgRzPVQGDdQUAA0kAMEYCIQDyrMUwKnBoyGBWAH2MxlJ2o8dzcJFKg/KtxoQH7+vMHwIhAKg43B7PDnLwXGqMoOTqjTczLKy/nrkpD2flMV8b23zs";
    }

    // 安庆市
    public static class AnQin {
        private AnQin() {
        }

        public static String sm2PrivateKey = "8a52bda36c9e589729263d1f8710e159f195d8b07b2653232a182d46d59e2e11";
        public static String base64Cert = "MIIBgDCCASWgAwIBAgIMSAYAAAABF2PI9SxVMAwGCCqBHM9VAYN1BQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAMMCXRlc3RjYXNtMjAeFw0yMzA4MjkwOTA2MDVaFw0yNDA4MjkwOTA2MDVaMB4xCzAJBgNVBAYTAkNOMQ8wDQYDVQQDDAblronluoYwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAARwQLc2saREQpAGXXFyDDBXkbafxqSUvwEdCljd9YI1wnhrM5yRQLkWDp1EsxM0pr9LZnDpg17qA12vC5mOdSjQo0QwQjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDgYDVR0PAQH/BAQDAgDAMBEGCWCGSAGG+EIBAQQEAwIAgDAMBggqgRzPVQGDdQUAA0cAMEQCIGQRqkzlsx0CS7PLJgV0I1Q9Inzx50wD+oS6PlUZJrJfAiA7SfbSoZu4Y4+Ao6Vre330qXf2WyIWZdmxfjP7RMdCNw==";
    }

    // 阜阳市
    public static class FuYang {
        private FuYang() {
        }

        public static String sm2PrivateKey = "b5adc5f616f46222dff95339faf5f59ec7cf53c79b4e1679088d89605eef290e";
        public static String base64Cert = "MIIBgTCCASWgAwIBAgIMSAYAAAABF2bhz7WfMAwGCCqBHM9VAYN1BQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAMMCXRlc3RjYXNtMjAeFw0yMzA4MjkwOTA2MDVaFw0yNDA4MjkwOTA2MDVaMB4xCzAJBgNVBAYTAkNOMQ8wDQYDVQQDDAbpmJzpmLMwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAARnc4cfi1LtHp2BdLfny551BKsPtn+pr5m9xUf/22yJrf2yI1gElJNuPSFdcuEzTsZK435HEushfe9R+DRxuGM/o0QwQjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDgYDVR0PAQH/BAQDAgDAMBEGCWCGSAGG+EIBAQQEAwIAgDAMBggqgRzPVQGDdQUAA0gAMEUCIFDqkgThu5EJ9oCJaa3iQGFsQopSWLThtBAbPQlmZ188AiEAnBrl5C/OczsIKAhYPAHq/QNoah/GoVM2HDqDLvTAxys=";
    }

    // 池州市
    public static class ChiZhou {
        private ChiZhou() {
        }

        public static String sm2PrivateKey = "5447246a21982efe4983c4bde77c19e4735cf6582dc10712e81e493b50e100fe";
        public static String base64Cert = "MIIBgjCCASWgAwIBAgIMSAYAAAABF2yMLnlFMAwGCCqBHM9VAYN1BQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAMMCXRlc3RjYXNtMjAeFw0yMzA4MjkwOTA2MDVaFw0yNDA4MjkwOTA2MDVaMB4xCzAJBgNVBAYTAkNOMQ8wDQYDVQQDDAbmsaDlt54wWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAARiLY0OyCrTHI2myEkgPWh4IAhvaBKQ5T4HTTexBFjI0VQiRVVaZE7Z1DYJvFHNQ2xUQGtsNahPajIy9ozvcC0co0QwQjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDgYDVR0PAQH/BAQDAgDAMBEGCWCGSAGG+EIBAQQEAwIAgDAMBggqgRzPVQGDdQUAA0kAMEYCIQCo5+eHswcZaGij9ROiJwyNNm3PW9CTDan+CCLF4N/wpgIhAN3G9OLYtKQTXMT38c40YjGngZ3jyQcwhOIyYL2hygNW";
    }

    // 黄山市
    public static class HuangShan {
        private HuangShan() {
        }

        public static String sm2PrivateKey = "67f89fec5fafedbaa99a1975df52a5640aa707f9d339914530e9a3c827c7ab15";
        public static String base64Cert = "MIIBgjCCASWgAwIBAgIMSAYAAAABF2++eiuNMAwGCCqBHM9VAYN1BQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAMMCXRlc3RjYXNtMjAeFw0yMzA4MjkwOTA2MDVaFw0yNDA4MjkwOTA2MDVaMB4xCzAJBgNVBAYTAkNOMQ8wDQYDVQQDDAbpu4TlsbEwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAARf0koyBw4ri/3zQzBnaqu4KgNKRJ2c7sKkWNQM63qIefu5BpyCYxbs1RnQd8JqoX8wqIdhwJrO7w1uYdFQesTIo0QwQjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDgYDVR0PAQH/BAQDAgDAMBEGCWCGSAGG+EIBAQQEAwIAgDAMBggqgRzPVQGDdQUAA0kAMEYCIQDGVS8J1z9e0u97bJR3mpKzhtKosIWL7mX8KGB/SI5SVgIhAJ2a3t6oDoMHd1lKh4NVAyrvAF6SwuUaClsDbg17Xeal";
    }

    // 宿州市
    public static class SuZhou {
        private SuZhou() {
        }

        public static String sm2PrivateKey = "639ed22531a1cea90074f01c6adaea3737b464212b815f5ad851b028e9598a65";
        public static String base64Cert = "MIIBgTCCASWgAwIBAgIMSAYAAAABF3KsWopbMAwGCCqBHM9VAYN1BQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAMMCXRlc3RjYXNtMjAeFw0yMzA4MjkwOTA2MDVaFw0yNDA4MjkwOTA2MDVaMB4xCzAJBgNVBAYTAkNOMQ8wDQYDVQQDDAblrr/lt54wWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAATZRTtGI4WIcLTp07e2ALbvHzuOzghExde/WPHG+MW7cTUErSI5qYCisJTABDhjeTp4KvXfcSdDxeq670SZfobjo0QwQjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDgYDVR0PAQH/BAQDAgDAMBEGCWCGSAGG+EIBAQQEAwIAgDAMBggqgRzPVQGDdQUAA0gAMEUCIQDaPUo4I1tnWK+zNKQwDwmoCkBcidF7P6gXKvYaU/R+JgIgAbdXzUCDV6HCwS6Bxw9HP4AYVTuO5awUBgSQQuP96Ak=";
    }

    // 铜陵市
    public static class TongLing {
        private TongLing() {
        }

        public static String sm2PrivateKey = "2a75c6403f10e409e55a2248b6dff3b0190bae5fb43ab7c977638b2ea19cb98a";
        public static String base64Cert = "MIIBgTCCASWgAwIBAgIMSAYAAAABF3XQxvAbMAwGCCqBHM9VAYN1BQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAMMCXRlc3RjYXNtMjAeFw0yMzA4MjkwOTA2MDVaFw0yNDA4MjkwOTA2MDVaMB4xCzAJBgNVBAYTAkNOMQ8wDQYDVQQDDAbpk5zpmbUwWTATBgcqhkjOPQIBBggqgRzPVQGCLQNCAAQ+5eUhdjp+aTtSUAzO26Uu8zlx4LA6f3MuozINiquOI7hmsGP3eTTeJ5Miu9j6Q1Rw+4T1xa/QXKnm50dbfeluo0QwQjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDgYDVR0PAQH/BAQDAgDAMBEGCWCGSAGG+EIBAQQEAwIAgDAMBggqgRzPVQGDdQUAA0gAMEUCIQC0s+9/ISEyezUMhTleA1pbczYx9QTUHJo0uHIARmdalgIgfuwfR8CDyQGJiLqdt43Px5TwuOMi6t5VN8ua8jMhr0k=";
    }
}
