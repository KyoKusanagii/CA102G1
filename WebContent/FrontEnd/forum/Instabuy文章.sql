DROP TABLE ARTICLE CASCADE CONSTRAINTS;
DROP SEQUENCE article_seq;
CREATE TABLE ARTICLE(
ARTI_NO     	    		VARCHAR2(10)  	NOT NULL,
ARTI_TOPIC 	    		VARCHAR2(100)  	NOT NULL,
MEM_NO    	     		VARCHAR2(10),
ARTI_CONTENT  		CLOB,
PO_TIME                 TIMESTAMP          NOT NULL,
CONSTRAINT ARTICLE_FK FOREIGN KEY(MEM_NO) REFERENCES  MEMBER(MEM_NO),
CONSTRAINT ARTICLE_PK PRIMARY KEY(ARTI_NO)
);
CREATE SEQUENCE article_seq
INCREMENT BY 1
START WITH 1
NOMAXVALUE
NOCYCLE
NOCACHE;

INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), 'David Seafood賣的海鮮不新鮮', 'M00001', '別堆砌懷念讓劇情
變得狗血
深愛了多年又何必
毀了經典

都已成年不拖不欠
浪費時間是我情願
像謝幕的演員
眼看著燈光熄滅

來不及
再轟轟烈烈
就保留
告別的尊嚴
我愛你不後悔
也尊重故事結尾

分手應該體面
誰都不要說抱歉
何來虧欠
我敢給就敢心碎
鏡頭前面是從前的我們
在喝彩
流著淚聲嘶力竭

離開也很體面
才沒辜負這些年
愛得熱烈
認真付出的畫面
別讓執念
毀掉了昨天
我愛過你
利落乾脆

最熟悉的街主角卻
換了人演
我哭到哽咽心再痛
就當破繭
來不及
再轟轟烈烈
就保留
告別的尊嚴
我愛你不後悔
也尊重故事結尾

分手應該體面
誰都不要說抱歉
何來虧欠
我敢給就敢心碎
鏡頭前面是從前的我們
在喝彩
流著淚聲嘶力竭

離開也很體面
才沒辜負這些年
愛得熱烈
認真付出的畫面
別讓執念
毀掉了昨天
我愛過你
利落乾脆
再見
不負遇見', to_timestamp('2018/07/01 12:02', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '產品問題', 'M00002', '書裡總愛寫到喜出望外的傍晚 
騎的單車還有他和她的對談 
女孩的白色衣裳男孩愛看她穿 
好多橋段 
好多都浪漫
好多人心酸
好聚好散
好多天都看不完
 
剛才吻了你一下你也喜歡對嗎
不然怎麼一直牽我的手不放
你說你好想帶我回去你的家鄉 
綠瓦紅磚 
柳樹和青苔
過去和現在
都一個樣
你說你也會這樣
 
慢慢喜歡你 
慢慢的親密 
慢慢聊自己
慢慢和你走在一起 
慢慢我想配合你 
慢慢把我給你 
 
慢慢喜歡你
慢慢的回憶 
慢慢的陪你慢慢的老去 
因為慢慢是個最好的原因 
 
晚餐後的甜點就點你喜歡的吧 
今晚就換你去床的右邊睡吧 
這次旅行我還想去上次的沙灘 
球鞋手錶
襪子和襯衫都已經燙好
放行李箱 
早上等著你起床 
 
慢慢喜歡你 
慢慢的親密 
慢慢聊自己 
慢慢和你走在一起 
慢慢我想配合你 
慢慢把我給你 
 
慢慢喜歡你
慢慢的回憶 
慢慢的陪你慢慢的老去 
因為慢慢是個最好的原因 
 
書裡總愛寫到喜出望外的傍晚 ', to_timestamp('2018/07/02 12:04', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '優良商品推薦', 'M00003', '記得那天  太陽壓著平原
風慢慢吹  沒有人掉眼淚
一切好美  好到我可以不用說話

金色的側臉    踩著全白球鞋
風繼續吹       世界繼續作業
那麼確定       我知道那就是你

那一天你走進了我的生命
謝謝你    成為了我的幾分之幾
閉上眼睛也能看見你
晴朗的南方

就算犯錯   你拿歲月等我
就算停留   還有你和夜空
我算什麼讓你無條件的為我

那一天你走進了我的生命
謝謝你    成為了我的幾分之幾
如果我又更完整一點
也是因為你

某一天你離開了我的生命
謝謝你曾經是我的幾分之幾
感覺你貼著我胸口呼吸
在那一個回不去的天明

我的   幾分之幾
你終於還是離開我的生命
留下每天都在變老的我
請記得我曾經 愛過', to_timestamp('2018/07/03 06:06', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), 'David粉站出來!!!!!', 'M00004', '菸一支一支一支的點　酒一杯一杯一杯的乾 
請你要體諒我　我酒量不好賣給我衝康 
時間一天一天一天的走　汗一滴一滴一滴的流 
有一天 咱都老　帶某子逗陣 
浪子回頭

親愛的　可愛的　英俊的　朋友 
垃圾的　沒品的　沒路用的　朋友

佇坎坷的路騎我兩光摩托車　橫豎我的人生甘哪狗屎 
我沒錢沒某沒子甘哪一條命　朋友阿　逗陣來搏

菸一支一支一支的點　酒一杯一杯一杯的乾 
請你要體諒我　我酒量不好賣給我衝康 
時間一天一天一天的走　汗一滴一滴一滴的流 
有一天　咱都老　帶某子逗陣 
浪子回頭

------------------------------------------

菸一支一支一支地點　酒一杯一杯一杯地乾
請你要體諒我　我酒量不好別給我挖坑
時間一天一天一天的走　汗一滴一滴一滴的流
有一天　我們都老　帶妻兒一起
浪子回頭

親愛的　可愛的　英俊的　朋友
垃圾的　沒品的　沒出息的　朋友

在坎坷的路騎我二流摩托車　反直我的人生像是狗屎
我沒錢沒妻沒子只有一條命　朋友阿　一起來賭吧

菸一支一支一支地點　酒一杯一杯一杯地乾
請你要體諒我　我酒量不好別給我挖坑
時間一天一天一天的走　汗一滴一滴一滴的流
有一天　我們都老　帶妻兒一起
浪子回頭

---------------', to_timestamp('2018/07/04 00:08', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '不要問,你會怕', 'M00005', '期待她今天是什麼打扮
其實就算沒有穿也好看
可能怕我東西弄不見 最好別冒險 整晚牽著女伴
旁邊都在問 她是誰 新的妹 幫她鋪餐巾
她是瘦 還是肥 我都不在意
別開車 不安全 因為在看妳
媽我把到女明星

根本藏不住妳 我藏不住妳 擔心被別人注意

SHE’S OVER STUNNED UNDER SPOTLIGHT
跟她上街變得好秋 像個明星戴著雷朋
感到溫暖 她的SPOTLIGHT
她的美只在我眼中 哎呀其他人不會懂
08被閃閃閃  閃閃瞎雙眸
他們被閃閃閃 閃閃瞎雙眸

當我見到她的時候我的精神都繃緊
羨煞旁人她是最美的風景
在夜裡 她亮著 即使離我好幾公里
再美的美女 我都屏蔽 當做空氣
散發氣質 不得不讓我低頭
看他吃冰使我口水多吞了幾口
直視著她 白色肩帶滑下
讓人融化 心目中蒙娜麗莎

根本藏不住妳 我藏不住妳 擔心被別人注意
根本藏不住妳 我藏不住妳 擔心被別人注意

SHE’S OVER STUNNED UNDER SPOTLIGHT
跟她上街變得好秋 像個明星戴著雷朋
感到溫暖 她的SPOTLIGHT
她的美只在我眼中 哎呀其他人不會懂

帶她出門 讓所有的人大吃ㄧ驚
路人反應 都覺得我牽著女明星
有她在 就算是沙漠也變成市中心
這天菜 讓這些男人宿敵回到叢林
怎麼可以正到我不用吃飯
美艷震到我的ㄧ舉一動就像痴漢
去哪都ㄧ直看 美得那?自然
工作都不想管 只想帶她繞地球轉

SHE’S OVER STUNNED UNDER SPOTLIGHT
跟她上街變得好秋 像個明星戴著雷朋
感到溫暖 她的SPOTLIGHT
她的美只在我眼中 哎呀其他人不會懂
08被閃閃閃  閃閃瞎雙眸
他們被閃閃閃 閃閃瞎雙眸', to_timestamp('2018/07/05 02:10', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '想請問退貨流程', 'M00006', '十張威力旺卡 GOLDEN TICKETS
我該把誰給帶著 與我同行者同進出
4 5 6 MILLIONS I’M GONNA SPLIT IT
讀了萬卷書 還不如行了萬里路
我高中肄業 九份工作只是跳板
賺我第一桶金 創業幫自己加薪
我絕不再領薪水 所以手臂刺滿
隨時都能退休 不用拼到半百
你不懂董仔不只在爽 他是一雙手
散沙能堆成山 也不用一鐘頭
拉住兄弟的手 今晚一起攻山頭
每一次的握手都能夠讓局面變不同

精壯 LIKE A KING KONG 地表高級 APE
捐掉我的衣裳 價值 100K 
我的城市需要更多愛 頻繁下雨天
跟上 YOU WILL LEARN SOME 這是 TRAINING DAY

I TAKE WHAT I WANT 弄髒自己手
沒有白吃的午餐 送到家門口
我靠腦子在賺錢 EVERYWHERE I GO
KA-CHING KA-CHING SUPER MARIO

少年董 少年董 吃得太好不敢再笑人胖
少年董 少年董 晚上出門帶幾個少年仔

少年董不藏私 兄弟ㄧ起賺
交易只用現金 不尬三點半
決策要果斷 賺錢不能縮肚 我來做典範
大膽出來混口飯 管他搬運磚頭還是掏扁?
不把話說滿 只為家庭能享福我勤點幹
阮整碗攏總捧 照顧甘苦人 建造兄弟避風港
兵來我將擋 把哥們帶上 就算前為死路 也拿命照樣闖
天道酬勤 做事要巴結
不走捷徑 章節輕鬆寫
做人如履薄冰 出口字字帶金
待人走心 享受倒吃甘蔗甜

少年董 少年董 吃得太好不敢再笑人胖
少年董 少年董 晚上出門帶幾個少年仔
少年董 少年董 吃得太好不敢再笑人胖
少年董 少年董 晚上出門帶幾個少年仔

我將雙手袖子捲起 不想再看誰臉色
用盡全力 不讓自己有點餓
建造 自己背景 少年仔的崛起
都是白手起 家欲戶曉 再多賺幾個零
別再只用眼神看 想著怎麼幹
月薪領幾萬 你早習慣 看著別人賺
拆掉名牌吊牌 OFF WHITE
COZY 賓士並排 BOSS LIFE
董仔喜歡解決問題 不論ㄧ通電話 還是見面三分情 
不是ㄧ夜致富 懂得打拼的心情 董仔之所以屌也是從底層拼起
看好直接框 不要左顧右盼
沙發左右排開都是開始的夥伴
同樣大的心 生意繞著我們轉
用真心在帶人 死後為我寫列傳

I TAKE WHAT I WANT 弄髒自己手
沒有白吃的午餐 送到家門口
我靠腦子在賺錢 EVERYWHERE I GO
KA-CHING KA-CHING SUPER MARIO

少年董 少年董 吃得太好不敢再笑人胖
少年董 少年董 晚上出門帶幾個少年仔', to_timestamp('2018/07/06 04:12', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '無聊的可以進來看看', 'M00007', '瘦子：我只擴大經營 罩大家庭 
直到我入土的那天請你節哀
BIG SHIT POPPING 
LITTLE SHIT STOPPING 
我們只幹大事 就像T.I.

我只幹大事 我幹大的
恁爸起大厝 我蓋大的
DAT ASS SO FAT 我幹大的
賓士AMG 我開大的

我在幹大事你不要亂
那些蠅頭小利留著 寧可不要賺
承認吧 沒有好處的活誰要幹
沒有載完整船黃金 我們不靠岸

女人想要鑽戒 五星級的飯店
小子沒有碰過活的拜金 沒有概念
WORK HARD 到半夜 其他人都在睡
我們來的地方 每個兄弟都想上位

誰管 HIP HOP 還不 HIP HOP 太小家子氣
國中還沒畢業 我就學會做生意
天生幹大事的少年董 比富二代爭氣
沒有含著金湯匙 超工時 瘋撈十億

我只擴大經營 罩大家庭 
直到我入土的那天請你節哀
BIG SHIT POPPING 
LITTLE SHIT STOPPING 
我們只幹大事 就像T.I.

我只幹大事 我幹大的
恁爸起大厝 我蓋大的
DAT ASS SO FAT 我幹大的
賓士AMG 我開大的

小春：
想要尬阮喬平大 拿錢來講
幹大事只看數目
沒在看小額利潤 嘿攏多講 
進帳塞爆我的帳戶
我從來不找誰靠 自己當作股票
幹大宗要夠狠 凍未條的咖小先跑
當我在家數鈔票 出手似闊少
現在換我跟阮兄弟主事攏無你的局
幹得高調才醒目 北七還搞不清楚
頂住幾十個億 服我的人都在同ㄧ路
插旗橫掃拓疆土 兄弟們幹遍江湖
擋我的發財路那些絆腳石全部都鏟除

我只擴大經營 罩大家庭 
直到我入土的那天請你節哀
BIG SHIT POPPING 
LITTLE SHIT STOPPING 
我們只幹大事 就像T.I.

我只幹大事 我幹大的
恁爸起大厝 我蓋大的
DAT ASS SO FAT 我幹大的
賓士AMG 我開大的


大淵：
我在養家活口 把現金奪走
快發牌結局要發財垃圾趕快挪走
擋路門都沒有 錢在跟你揮手
莎呦那啦別想發達把你馬子虧走
沒 在 怕 事 我都幹 
胸 懷 大 志 賺錢不偷懶
享受過後我才不想餬口飯
心狠手辣要幹大事得不擇手段
拼了命再往上闖盪 我的狀況狂妄
老鄉榜樣 傳遍在地方上
花光不緊張 因?ㄧ直進帳 
豐收景象 就像挖到金礦

我只擴大經營 罩大家庭 
直到我入土的那天請你節哀
BIG SHIT POPPING 
LITTLE SHIT STOPPING 
我們只幹大事 就像T.I.

我只幹大事 我幹大的
恁爸起大厝 我蓋大的
DAT ASS SO FAT 我幹大的
賓士AMG 我開大的', to_timestamp('2018/07/07 03:14', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '有沒有JAVA老師變成網路紅人的八卦?', 'M00008', '伊係北部的口音 搖落
中部的口音 搖落 搖落
南部的口音 搖落 搖落

音樂插播 看她脫掉上衣
音樂插播 看她脫掉上衣 她是

揮霍一切直到沒
這個單位不太歡迎小氣鬼
幾十萬的現金放在口袋不用回
今天晚上就算有家也不用回
82年的拉菲 生牛肉配上松露
你笑我世俗我也不要空腹
一條龍的帳單簽個名我都付
從錦州街口一路玩到忠孝東路
年輕人的小費都往口袋塞
黃董太HIGH
誰還躲在角落講電話應付太太
最好早點回家這條狗太乖

酒色財氣的台北 戴著純金的項鍊
幫我排新的小姐 辣台妹
酒色財氣的台北 戴著純金的項鍊
幫我排新的小姐 尬台妹

辣台妹 辣台妹 騷底
辣台妹 辣台妹 WU
辣台妹 辣台妹 騷底
辣台妹 辣台妹 搖落

伊係北部的口音 搖落
中部的口音 搖落 搖落
南部的口音 搖落 搖落

音樂插播 看她脫掉上衣
音樂插播 看她脫掉上衣 她是

我們不說價 敢玩再坐下
給我一盤SHOTS 看我贏光妳的衣服全部都脫下
在這晚上我們不講人情 
最好只講現金 整場美女如雲
喝到早上 直到是誰不行
全部都框到底 就讓我來處理
最熱情的鄉音 身價都是鑲金 
為了她們我要繼續再開幾支香檳
快點脫掉上衣 散發迷人香氣
把錢通通都給妳別再說妳爸生病

酒色財氣的台北 戴著純金的項鍊
幫我排新的小姐 辣台妹
酒色財氣的台北 戴著純金的項鍊
幫我排新的小姐 尬台妹

辣台妹 辣台妹 騷底
辣台妹 辣台妹 WU
辣台妹 辣台妹 騷底
辣台妹 辣台妹 搖落

伊係北部的口音 搖落 
中部的口音 搖落 搖落
南部的口音 搖落 搖落

音樂插播 看她脫掉上衣
音樂插播 看她脫掉上衣 她是

手戴水鬼 脖子戴上金飾
不搭捷運 最好別刮我的賓士
待會要去哪耍 得看我興致
為何換帖兄弟全都移去麗緻
敢玩的小姐 恁全部照排
裝一塊的紅牌今嘜雄厚嘜來
朋友覓食來談戀愛 昏頭掉進懸崖
刷卡外框放生 下次帶包再來
走店絕不簽單 打腫臉充胖子
出門在外 沒在怕那出亂子
阮敢玩就敢博 今天當浪子
小姐看到我們像是吃到倒吊籽

酒色財氣的台北 戴著純金的項鍊
幫我排新的小姐 辣台妹
酒色財氣的台北 戴著純金的項鍊
幫我排新的小姐 尬台妹

辣台妹 辣台妹 騷底
辣台妹 辣台妹 WU
辣台妹 辣台妹 騷底
辣台妹 辣台妹 搖落

伊係北部的口音 搖落
中部的口音 搖落 搖落
南部的口音 搖落 搖落

音樂插播 看她脫掉上衣 
音樂插播 看她脫掉上衣', to_timestamp('2018/07/08 12:16', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '教你如何挑選CP值最高的產品', 'M00009', '寒流來了  剛好
剛好可以  把你手放外套
把安全帽戴好
不讓你在  愛情路上跌倒

加速狂飆  你說不要
愛的險你不想冒
不想被套牢
他的懷抱  真比我好？
你幸福就好

不愛我就拉倒
離開之前
不要愛的抱抱
反正  我又不是沒有人要
哥練的胸肌
如果你還想靠
好膽你就麥造

寒流來了  剛好可以  把你手放外套
把安全帽戴好  不要在愛情路上跌倒
加速狂飆  你  你說不要
愛的險你不想冒  不想被我給套牢
反正我又不是沒有人要
哥練的胸肌如果你還想靠
算了  你幸福你幸福就好

寒流來了  剛好
剛好可以  把你手放外套
把安全帽戴好
不讓你在  愛情路上跌倒

加速狂飆  你說不要
愛的險你不想冒
不想被套牢
他的懷抱  真比我好？
你幸福就好

不愛我就拉倒
離開之前
不要愛的抱抱
反正  我又不是沒有人要
哥練的胸肌
如果你還想靠
好膽你就麥造

你幸福就好

不愛我就拉倒
離開之前
不要愛的抱抱
反正  我又不是沒有人要
哥練的胸肌
如果你還想靠
好膽你就麥造', to_timestamp('2018/07/09 12:18', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '想買運動用品的可以進來看看!', 'M00005', '當08第一天走跳
(SOUTH SIDE) (RAIN CITY)
NAH NAH THEY DON’T UNDERSTAND I GOTTA SHOW OUT
(SOUTH SIDE)
當08第一天走跳
(SOUTH SIDE) (RAIN CITY)
WUUUUUUU MAN I GOTTA SHOW OUT
(SOUTH SIDE)

NOW HOLD UP
別跟我開玩笑 這遊戲 SHOW NO MERCY
給我一點樂子 這裡沒有人能匹敵
一身銅臭味 髒得沒有人在清理
你不喜歡我也不喜歡你 快滾 沒有人在逼你
恁爸來就是欲殺 一定愛改
天是棺材蓋 你哪個公司派來 說實在 是多壞
他說我性底壞 還嗆他 I WILL SEE YOU OUTSIDE
是哦是哦 LIL BITCH SO THE FUCK WHAT

我可以吃虧也不怕被人佔便宜
下次加倍奉還 這價格 YOU NEED TO CUT IT
先下手為強 別傻傻在那等人整你
禍不單行 我請你先擔心你自己 沒出息的生意
十年見多成敗不見怪
時機不對的好貨也被人賤賣
派對像個明星 我們都是醉漢
昨天發生什麼事 我記憶只有片段

當08第一天走跳
(SOUTH SIDE) (RAIN CITY)
NAH NAH THEY DON’T UNDERSTAND I GOTTA SHOW OUT
(SOUTH SIDE)
當08第一天走跳
(SOUTH SIDE) (RAIN CITY)
WUUUUUUU MAN I GOTTA SHOW OUT
(SOUTH SIDE)

跳 MOTHER FUCK 跳 MOTHER FUCK 跳
跳 MOTHER FUCK 跳 MOTHER FUCK 跳
跳 MOTHER FUCK 跳 MOTHER FUCK 跳
跳 MOTHER FUCK 跳 MOTHER FUCK 跳

儘管老子一身傲氣 還得學會謙卑
吞下交際喝的酒 已不下千杯
彎下身軀低頭看這世界還能頂天
看著自視甚高 小鬼還能夠快活幾年
他們剛開始出來?? 幻想站在頂樓
泉整堆 自己多勇 全是無中生有
出來展 要懂先後 吃相別太過醜陋
面對我臉皮別太薄 看你能值幾兩重
別再想著借酒裝瘋 記住那天的糗
人不犯我 我不犯人 依然低調喝著酒
敵不動 我不動 心裡讓人猜不透
上好放予尊重會比較好過 莫來共我恐
沒在信道 別當我是病貓 你儘早
想想這盤棋子該要怎麼和我下
今朝 下起冰雹 戴上笠帽
關在無聲的所在 薑還是老的辣

當08第一天走跳
(SOUTH SIDE) (RAIN CITY)
NAH NAH THEY DON’T UNDERSTAND I GOTTA SHOW OUT
(SOUTH SIDE)
當08第一天走跳
(SOUTH SIDE) (RAIN CITY)
WUUUUUUU MAN I GOTTA SHOW OUT
(SOUTH SIDE)

走跳10年沒有後悔問心無愧
他們還在意當年誤會是對還是不對
如果視野只剩下愛與和平的世界
也不會有人在意你當年是否要出櫃

少裝腔作勢 你們裝ㄎㄧㄤ說故事
我曾經也像你們ㄧ樣 想趨炎附勢
能得到名利物質 我變得沒有素質
他讓我吃得太好 不信你們看我肚子

我將存款數字 不停複製貼上
誰說我忘本 我依然混在那條街上
每逢中秋 我們辦趴在堤防邊上
歡迎我所有的老鄉ㄧ起過來法大

見過世面 在社會看過各種事件
我知道哪些是朋友 哪些只會見ㄧ次面
就是又有人拿我們的照片招搖撞騙
蛤 我們是朋友嗎 路上別被我撞見

當08第一天走跳

跳 MOTHER FUCK 跳 MOTHER FUCK 跳
跳 MOTHER FUCK 跳 MOTHER FUCK 跳
跳 MOTHER FUCK 跳 MOTHER FUCK 跳
跳 MOTHER FUCK 跳 MOTHER FUCK 

當08第一天走跳

跳 MOTHER FUCK 跳 MOTHER FUCK 跳
跳 MOTHER FUCK 跳 MOTHER FUCK 跳
跳 MOTHER FUCK 跳 MOTHER FUCK 跳
跳 MOTHER FUCK 跳 MOTHER FUCK 跳

當08第一天走跳', to_timestamp('2018/07/10 06:20', 'YYYY/MM/DD HH24:MI'));
commit;