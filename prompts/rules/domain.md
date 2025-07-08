架构描述
=======
当前技术栈为 Java
为了使 /dir:domain/src/main/java/autodev/ddd/platform/model 下的代码和 uml 模型一一对应，请按照以下规则生成文件
- 对于实体本身，创建纯粹的 Java Pojo 类
- 实体第一个参数为 identity，表示实体的身份
- 实体第二个参数为 description，使用 record 类，封装 identity 的属性
- 实体第三个参数起，如何和其它实体为聚合/组合关系，则创建关联接口
  - 比如 Purchaser *-- Purchase 在 Purchaser 下创建 PurchaserPurchases 接口
  - 参考代码为 /file:domain/src/main/java/autodev/ddd/platform/model/Purchaser.java
- 如果实体之间是 .. 关系，则需要实体聚合中创建 context 接口
  - 比如 User .. Purchaser，Purchaser 在 PurchaseContext 中，则需要创建 PurchaserContext 接口
  - 参考代码为 /file:domain/src/main/java/autodev/ddd/platform/model/Users.java 下的 interface PurchaserContext


====
首先，列出每一个验收场景以及对应的测试数据；
然后，针对每一个验收场景，按照架构描述和工序说明的指引，列出任务列表。