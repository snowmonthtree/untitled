public class CustomIdGeneratorTest {
/*
        private CustomStringIdGenerator generator;
        private SharedSessionContractImplementor sessionMock;

        @BeforeEach
        public void setUp() {
            generator = new CustomStringIdGenerator();
            sessionMock = Mockito.mock(SharedSessionContractImplementor.class);
        }

        @Test
        public void testGenerateReturnsValidUUID() throws HibernateException {
            // 调用 generate 方法
            Serializable uuid = generator.generate(sessionMock, null);
            System.out.println(uuid);

            // 验证返回值是一个字符串
            assertNotNull(uuid, "UUID should not be null");
            assertTrue(uuid instanceof String, "Returned value should be a string");

            // 验证返回的字符串为数字，并且在指定的范围内
            try {
                int uuidInt = Integer.parseInt((String) uuid);
                assertTrue(uuidInt >= 0 && uuidInt <= 100000000, "UUID should be within the valid range");
            } catch (NumberFormatException e) {
                fail("Returned UUID should be a valid integer string");
            }
        }

        @Test
        public void testGenerateProducesDifferentValues() throws HibernateException {
            // 生成两个 UUID，确保它们不同
            Serializable uuid1 = generator.generate(sessionMock, null);
            Serializable uuid2 = generator.generate(sessionMock, null);

            assertNotEquals(uuid1, uuid2, "UUIDs should be different");
        }

*/
}
