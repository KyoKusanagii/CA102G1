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
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), 'David Seafood�檺���A���s�A', 'M00001', '�O����h�����@��
�ܱo����
�`�R�F�h�~�S��
���F�g��

���w���~���줣��
���O�ɶ��O�ڱ��@
���¹����t��
���ݵۿO������

�Ӥ���
�A�F�F�P�P
�N�O�d
�i�O���L�Y
�ڷR�A���ᮬ
�]�L���G�Ƶ���

���������魱
�ֳ����n����p
�������
�ڴ����N���߸H
���Y�e���O�q�e���ڭ�
�b�ܱm
�y�۲\�n�R�O��

���}�]���魱
�~�S�d�t�o�Ǧ~
�R�o���P
�{�u�I�X���e��
�O������
�����F�Q��
�ڷR�L�A
�Q������

�̼��x����D���o
���F�H�t
�ڭ���C�|�ߦA�h
�N��}õ
�Ӥ���
�A�F�F�P�P
�N�O�d
�i�O���L�Y
�ڷR�A���ᮬ
�]�L���G�Ƶ���

���������魱
�ֳ����n����p
�������
�ڴ����N���߸H
���Y�e���O�q�e���ڭ�
�b�ܱm
�y�۲\�n�R�O��

���}�]���魱
�~�S�d�t�o�Ǧ~
�R�o���P
�{�u�I�X���e��
�O������
�����F�Q��
�ڷR�L�A
�Q������
�A��
���t�J��', to_timestamp('2018/07/01 12:02', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '���~���D', 'M00002', '�Ѹ��`�R�g��ߥX��~���ı� 
�M���樮�٦��L�M�o����� 
�k�Ī��զ��n�k�ķR�ݦo�� 
�n�h���q 
�n�h������
�n�h�H�߻�
�n�E�n��
�n�h�ѳ��ݤ���
 
��~�k�F�A�@�U�A�]���w���
���M���@���o�ڪ��⤣��
�A���A�n�Q�a�ڦ^�h�A���a�m 
��ˬ��j 
�h��M�C�a
�L�h�M�{�b
���@�Ӽ�
�A���A�]�|�o��
 
�C�C���w�A 
�C�C���˱K 
�C�C��ۤv
�C�C�M�A���b�@�_ 
�C�C�ڷQ�t�X�A 
�C�C��ڵ��A 
 
�C�C���w�A
�C�C���^�� 
�C�C�����A�C�C���ѥh 
�]���C�C�O�ӳ̦n����] 
 
���\�᪺���I�N�I�A���w���a 
���ߴN���A�h�ɪ��k��Χa 
�o���Ȧ���ٷQ�h�W�����F�y 
�y�c���
���l�MŨ�m���w�g�S�n
�����c 
���W���ۧA�_�� 
 
�C�C���w�A 
�C�C���˱K 
�C�C��ۤv 
�C�C�M�A���b�@�_ 
�C�C�ڷQ�t�X�A 
�C�C��ڵ��A 
 
�C�C���w�A
�C�C���^�� 
�C�C�����A�C�C���ѥh 
�]���C�C�O�ӳ̦n����] 
 
�Ѹ��`�R�g��ߥX��~���ı� ', to_timestamp('2018/07/02 12:04', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '�u�}�ӫ~����', 'M00003', '�O�o����  �Ӷ����ۥ���
���C�C�j  �S���H�����\
�@���n��  �n��ڥi�H���λ���

���⪺���y    ��ۥ��ղy�c
���~��j       �@���~��@�~
����T�w       �ڪ��D���N�O�A

���@�ѧA���i�F�ڪ��ͩR
���§A    �����F�ڪ��X�����X
���W�����]��ݨ��A
���Ԫ��n��

�N��ǿ�   �A�����뵥��
�N�ⰱ�d   �٦��A�M�]��
�ں⤰�����A�L���󪺬���

���@�ѧA���i�F�ڪ��ͩR
���§A    �����F�ڪ��X�����X
�p�G�ڤS�󧹾�@�I
�]�O�]���A

�Y�@�ѧA���}�F�ڪ��ͩR
���§A���g�O�ڪ��X�����X
�Pı�A�K�ۧگݤf�I�l
�b���@�Ӧ^���h���ѩ�

�ڪ�   �X�����X
�A�ש��٬O���}�ڪ��ͩR
�d�U�C�ѳ��b�ܦѪ���
�аO�o�ڴ��g �R�L', to_timestamp('2018/07/03 06:06', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), 'David�����X��!!!!!', 'M00004', '�Ҥ@��@��@�䪺�I�@�s�@�M�@�M�@�M���� 
�ЧA�n��̧ڡ@�ڰs�q���n�浹�ڽıd 
�ɶ��@�Ѥ@�Ѥ@�Ѫ����@���@�w�@�w�@�w���y 
���@�� �����ѡ@�a�Y�l�r�} 
���l�^�Y

�˷R���@�i�R���@�^�T���@�B�� 
�U�����@�S�~���@�S���Ϊ��@�B��

��V�����M�ڨ���������@��ݧڪ��H�̭ͥ����� 
�ڨS���S�Y�S�l�̭��@���R�@�B�ͪ��@�r�}�ӷi

�Ҥ@��@��@�䪺�I�@�s�@�M�@�M�@�M���� 
�ЧA�n��̧ڡ@�ڰs�q���n�浹�ڽıd 
�ɶ��@�Ѥ@�Ѥ@�Ѫ����@���@�w�@�w�@�w���y 
���@�ѡ@�����ѡ@�a�Y�l�r�} 
���l�^�Y

------------------------------------------

�Ҥ@��@��@��a�I�@�s�@�M�@�M�@�M�a��
�ЧA�n��̧ڡ@�ڰs�q���n�O���ګ��|
�ɶ��@�Ѥ@�Ѥ@�Ѫ����@���@�w�@�w�@�w���y
���@�ѡ@�ڭ̳��ѡ@�a�d��@�_
���l�^�Y

�˷R���@�i�R���@�^�T���@�B��
�U�����@�S�~���@�S�X�����@�B��

�b���V�����M�ڤG�y�������@�Ϫ��ڪ��H�͹��O����
�ڨS���S�d�S�l�u���@���R�@�B�ͪ��@�@�_�ӽ�a

�Ҥ@��@��@��a�I�@�s�@�M�@�M�@�M�a��
�ЧA�n��̧ڡ@�ڰs�q���n�O���ګ��|
�ɶ��@�Ѥ@�Ѥ@�Ѫ����@���@�w�@�w�@�w���y
���@�ѡ@�ڭ̳��ѡ@�a�d��@�_
���l�^�Y

---------------', to_timestamp('2018/07/04 00:08', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '���n��,�A�|��', 'M00005', '���ݦo���ѬO���򥴧�
���N��S����]�n��
�i��ȧڪF��ˤ��� �̦n�O�_�I ��߲o�ۤk��
���䳣�b�� �o�O�� �s���f ���o�Q�\�y
�o�O�G �٬O�� �ڳ����b�N
�O�}�� ���w�� �]���b�ݩp
���ڧ��k���P

�ڥ��ä���p ���ä���p ��߳Q�O�H�`�N

SHE��S OVER STUNNED UNDER SPOTLIGHT
��o�W���ܱo�n�� ���ө��P���۹p�B
�P��ŷx �o��SPOTLIGHT
�o�����u�b�ڲ��� �u�r��L�H���|��
08�Q�{�{�{  �{�{�M����
�L�̳Q�{�{�{ �{�{�M����

��ڨ���o���ɭԧڪ��믫���^��
�r�ٮǤH�o�O�̬�������
�b�]�� �o�G�� �Y�����ڦn�X����
�A�������k �ڳ��̽� ���Ů�
���o��� ���o�����ڧC�Y
�ݥL�Y�B�ϧڤf���h�]�F�X�f
�����ۦo �զ�ӱa�ƤU
���H�Ĥ� �ߥؤ��X�R�R��

�ڥ��ä���p ���ä���p ��߳Q�O�H�`�N
�ڥ��ä���p ���ä���p ��߳Q�O�H�`�N

SHE��S OVER STUNNED UNDER SPOTLIGHT
��o�W���ܱo�n�� ���ө��P���۹p�B
�P��ŷx �o��SPOTLIGHT
�o�����u�b�ڲ��� �u�r��L�H���|��

�a�o�X�� ���Ҧ����H�j�Y����
���H���� ��ı�o�ڲo�ۤk���P
���o�b �N��O�F�z�]�ܦ�������
�o�ѵ� ���o�Ǩk�H�J�Ħ^���O�L
���i�H����ڤ��ΦY��
���A�_��ڪ����|�@�ʴN�����~
�h���������� ���o��?�۵M
�u�@�����Q�� �u�Q�a�o¶�a�y��

SHE��S OVER STUNNED UNDER SPOTLIGHT
��o�W���ܱo�n�� ���ө��P���۹p�B
�P��ŷx �o��SPOTLIGHT
�o�����u�b�ڲ��� �u�r��L�H���|��
08�Q�{�{�{  �{�{�M����
�L�̳Q�{�{�{ �{�{�M����', to_timestamp('2018/07/05 02:10', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '�Q�аݰh�f�y�{', 'M00006', '�Q�i�¤O���d GOLDEN TICKETS
�ڸӧ�ֵ��a�� �P�ڦP��̦P�i�X
4 5 6 MILLIONS I��M GONNA SPLIT IT
Ū�F�U���� �٤��p��F�U����
�ڰ����w�~ �E���u�@�u�O���O
�ȧڲĤ@��� �з~���ۤv�[�~
�ڵ����A���~�� �ҥH���u�뺡
�H�ɳ���h�� ���Ϋ���b��
�A�������J���u�b�n �L�O�@����
���F��令�s �]���Τ@���Y
�Ԧ�S�̪��� ���ߤ@�_��s�Y
�C�@�������ⳣ����������ܤ��P

�맧 LIKE A KING KONG �a���� APE
�����ڪ���n ���� 100K 
�ڪ������ݭn��h�R �W�c�U�B��
��W YOU WILL LEARN SOME �o�O TRAINING DAY

I TAKE WHAT I WANT ��ż�ۤv��
�S���զY�����\ �e��a���f
�ھa���l�b�ȿ� EVERYWHERE I GO
KA-CHING KA-CHING SUPER MARIO

�֦~�� �֦~�� �Y�o�Ӧn�����A���H�D
�֦~�� �֦~�� �ߤW�X���a�X�Ӥ֦~�J

�֦~�����èp �S�̣��_��
����u�β{�� �����T�I�b
�M���n�G�_ �ȿ������Y�{ �ڨӰ���d
�j�x�X�ӲV�f�� �ޥL�h�B�j�Y�٬O�ǫ�?
����ܻ��� �u���a�x��ɺ֧ڶ��I�F
����J�l�`�� ���U�̭W�H �سy�S���׭���
�L�ӧڱN�� ����̱a�W �N��e������ �]���R�Ӽ���
�ѹD�S�� ���ƭn�ڵ�
�������| ���`���P�g
���H�p�i���B �X�f�r�r�a��
�ݤH���� �ɨ��˦Y�̽���

�֦~�� �֦~�� �Y�o�Ӧn�����A���H�D
�֦~�� �֦~�� �ߤW�X���a�X�Ӥ֦~�J
�֦~�� �֦~�� �Y�o�Ӧn�����A���H�D
�֦~�� �֦~�� �ߤW�X���a�X�Ӥ֦~�J

�ڱN����S�l���_ ���Q�A�ݽ��y��
�κɥ��O �����ۤv���I�j
�سy �ۤv�I�� �֦~�J���U�_
���O�դ�_ �a����� �A�h�ȴX�ӹs
�O�A�u�β����� �Q�۫��F
���~��X�U �A���ߺD �ݵۧO�H��
��W�P�Q�P OFF WHITE
COZY ���h�ñ� BOSS LIFE
���J���w�ѨM���D ���ף��q�q�� �٬O�����T���� 
���O���]�P�I ���o�������߱� ���J���ҥH�x�]�O�q���h���_
�ݦn������ ���n���U�k��
�F�o���k�ƶ}���O�}�l���٦�
�P�ˤj���� �ͷN¶�ۧڭ���
�ίu�ߦb�a�H ���ᬰ�ڼg�C��

I TAKE WHAT I WANT ��ż�ۤv��
�S���զY�����\ �e��a���f
�ھa���l�b�ȿ� EVERYWHERE I GO
KA-CHING KA-CHING SUPER MARIO

�֦~�� �֦~�� �Y�o�Ӧn�����A���H�D
�֦~�� �֦~�� �ߤW�X���a�X�Ӥ֦~�J', to_timestamp('2018/07/06 04:12', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '�L�᪺�i�H�i�Ӭݬ�', 'M00007', '�G�l�G�ڥu�X�j�g�� �n�j�a�x 
����ڤJ�g�����ѽЧA�`�s
BIG SHIT POPPING 
LITTLE SHIT STOPPING 
�ڭ̥u�F�j�� �N��T.I.

�ڥu�F�j�� �ڷF�j��
ѡ���_�j�� �ڻ\�j��
DAT ASS SO FAT �ڷF�j��
���hAMG �ڶ}�j��

�ڦb�F�j�ƧA���n��
�������Y�p�Q�d�� ��i���n��
�ӻ{�a �S���n�B�����֭n�F
�S������������ �ڭ̤��a��

�k�H�Q�n�p�� ���P�Ū�����
�p�l�S���I�L�������� �S������
WORK HARD ��b�] ��L�H���b��
�ڭ̨Ӫ��a�� �C�ӥS�̳��Q�W��

�ֺ� HIP HOP �٤� HIP HOP �Ӥp�a�l��
�ꤤ�٨S���~ �ڴN�Ƿ|���ͷN
�ѥͷF�j�ƪ��֦~�� ��I�G�N����
�S���t�۪����� �W�u�� �Ƽ��Q��

�ڥu�X�j�g�� �n�j�a�x 
����ڤJ�g�����ѽЧA�`�s
BIG SHIT POPPING 
LITTLE SHIT STOPPING 
�ڭ̥u�F�j�� �N��T.I.

�ڥu�F�j�� �ڷF�j��
ѡ���_�j�� �ڻ\�j��
DAT ASS SO FAT �ڷF�j��
���hAMG �ڶ}�j��

�p�K�G
�Q�n�����쥭�j ��������
�F�j�ƥu�ݼƥ�
�S�b�ݤp�B�Q�� �K�l�h�� 
�i�b���z�ڪ��b��
�ڱq�Ӥ���־a �ۤv��@�Ѳ�
�F�j�v�n���� �᥼�����@�p���]
��ڦb�a�ƶr�� �X������
�{�b���ڸ򨿥S�̥D���l�L�A����
�F�o���դ~���� �_�C�ٷd���M��
����X�Q�ӻ� �A�ڪ��H���b�P����
���X���æ�g �S�̷̭F�M����
�קڪ��o�]�����ǲ̸}�ۥ������갣

�ڥu�X�j�g�� �n�j�a�x 
����ڤJ�g�����ѽЧA�`�s
BIG SHIT POPPING 
LITTLE SHIT STOPPING 
�ڭ̥u�F�j�� �N��T.I.

�ڥu�F�j�� �ڷF�j��
ѡ���_�j�� �ڻ\�j��
DAT ASS SO FAT �ڷF�j��
���hAMG �ڶ}�j��


�j�W�G
�ڦb�i�a���f ��{���ܨ�
�ֵo�P�����n�o�]�U�����֮���
�׸������S�� ���b��A����
���稺�էO�Q�o�F��A���l����
�S �b �� �� �ڳ��F 
�� �h �j �� �ȿ������i
�ɨ��L��ڤ~���Q�M�f��
�߬��⻶�n�F�j�Ʊo���ܤ�q
���F�R�A���W���� �ڪ����p�g�k
�Ѷm�]�� �ǹM�b�a��W
�������i �]?�����i�b 
�צ����H �N��������q

�ڥu�X�j�g�� �n�j�a�x 
����ڤJ�g�����ѽЧA�`�s
BIG SHIT POPPING 
LITTLE SHIT STOPPING 
�ڭ̥u�F�j�� �N��T.I.

�ڥu�F�j�� �ڷF�j��
ѡ���_�j�� �ڻ\�j��
DAT ASS SO FAT �ڷF�j��
���hAMG �ڶ}�j��', to_timestamp('2018/07/07 03:14', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '���S��JAVA�Ѯv�ܦ��������H���K��?', 'M00008', '��Y�_�����f�� �n��
�������f�� �n�� �n��
�n�����f�� �n�� �n��

���ִ��� �ݦo�汼�W��
���ִ��� �ݦo�汼�W�� �o�O

���N�@������S
�o�ӳ�줣���w��p��
�X�Q�U���{����b�f�U���Φ^
���ѱߤW�N�⦳�a�]���Φ^
82�~���Ե� �ͤ��װt�W�Q�S
�A���ڥ@�U�ڤ]���n�Ÿ�
�@���s���b��ñ�ӦW�ڳ��I
�q�A�{��f�@�����쩾���F��
�~���H���p�O�����f�U��
������HIGH
���ٸ��b�������q�����I�Ӥ�
�̦n���I�^�a�o�����Ө�

�s��]�𪺥x�_ ���ۯª�������
���ڱƷs���p�j ���x�f
�s��]�𪺥x�_ ���ۯª�������
���ڱƷs���p�j ���x�f

���x�f ���x�f �̩�
���x�f ���x�f WU
���x�f ���x�f �̩�
���x�f ���x�f �n��

��Y�_�����f�� �n��
�������f�� �n�� �n��
�n�����f�� �n�� �n��

���ִ��� �ݦo�汼�W��
���ִ��� �ݦo�汼�W�� �o�O

�ڭ̤����� �����A���U
���ڤ@�LSHOTS �ݧ�Ĺ���p����A��������U
�b�o�ߤW�ڭ̤����H�� 
�̦n�u���{�� ������k�p��
�ܨ즭�W ����O�֤���
�������ب쩳 �N���ڨӳB�z
�̼������m�� �������O�^�� 
���F�o�̧ڭn�~��A�}�X�䭻�b
���I�汼�W�� ���o�g�H����
����q�q�����p�O�A���p���ͯf

�s��]�𪺥x�_ ���ۯª�������
���ڱƷs���p�j ���x�f
�s��]�𪺥x�_ ���ۯª�������
���ڱƷs���p�j ���x�f

���x�f ���x�f �̩�
���x�f ���x�f WU
���x�f ���x�f �̩�
���x�f ���x�f �n��

��Y�_�����f�� �n�� 
�������f�� �n�� �n��
�n�����f�� �n�� �n��

���ִ��� �ݦo�汼�W��
���ִ��� �ݦo�汼�W�� �o�O

�������� ��l���W����
���f���B �̦n�O��ڪ����h
�ݷ|�n�h���A �o�ݧڿ��P
���󴫩��S�̥������h�R�o
�������p�j ѡ�����ӱ�
�ˤ@�������P���M���p�M��
�B�ͳV���ӽ��ʷR ���Y���i�a�V
��d�~�ة�� �U���a�]�A��
��������ñ�� ���~�y�R�D�l
�X���b�~ �S�b�Ȩ��X�äl
�������N���� ���ѷ���l
�p�j�ݨ�ڭ̹��O�Y��˦Q��

�s��]�𪺥x�_ ���ۯª�������
���ڱƷs���p�j ���x�f
�s��]�𪺥x�_ ���ۯª�������
���ڱƷs���p�j ���x�f

���x�f ���x�f �̩�
���x�f ���x�f WU
���x�f ���x�f �̩�
���x�f ���x�f �n��

��Y�_�����f�� �n��
�������f�� �n�� �n��
�n�����f�� �n�� �n��

���ִ��� �ݦo�汼�W�� 
���ִ��� �ݦo�汼�W��', to_timestamp('2018/07/08 12:16', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '�ЧA�p��D��CP�ȳ̰������~', 'M00009', '�H�y�ӤF  ��n
��n�i�H  ��A���~�M
��w���U���n
�����A�b  �R�����W�^��

�[�t�g�t  �A�����n
�R���I�A���Q�_
���Q�Q�M�c
�L���h��  �u��ڦn�H
�A���ִN�n

���R�ڴN�ԭ�
���}���e
���n�R�����
�ϥ�  �ڤS���O�S���H�n
���m���ݦ�
�p�G�A�ٷQ�a
�n�x�A�N���y

�H�y�ӤF  ��n�i�H  ��A���~�M
��w���U���n  ���n�b�R�����W�^��
�[�t�g�t  �A  �A�����n
�R���I�A���Q�_  ���Q�Q�ڵ��M�c
�ϥ��ڤS���O�S���H�n
���m���ݦ٦p�G�A�ٷQ�a
��F  �A���֧A���ִN�n

�H�y�ӤF  ��n
��n�i�H  ��A���~�M
��w���U���n
�����A�b  �R�����W�^��

�[�t�g�t  �A�����n
�R���I�A���Q�_
���Q�Q�M�c
�L���h��  �u��ڦn�H
�A���ִN�n

���R�ڴN�ԭ�
���}���e
���n�R�����
�ϥ�  �ڤS���O�S���H�n
���m���ݦ�
�p�G�A�ٷQ�a
�n�x�A�N���y

�A���ִN�n

���R�ڴN�ԭ�
���}���e
���n�R�����
�ϥ�  �ڤS���O�S���H�n
���m���ݦ�
�p�G�A�ٷQ�a
�n�x�A�N���y', to_timestamp('2018/07/09 12:18', 'YYYY/MM/DD HH24:MI'));
INSERT INTO ARTICLE (ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME)
	VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), '�Q�R�B�ʥΫ~���i�H�i�Ӭݬ�!', 'M00005', '��08�Ĥ@�Ѩ���
(SOUTH SIDE) (RAIN CITY)
NAH NAH THEY DON��T UNDERSTAND I GOTTA SHOW OUT
(SOUTH SIDE)
��08�Ĥ@�Ѩ���
(SOUTH SIDE) (RAIN CITY)
WUUUUUUU MAN I GOTTA SHOW OUT
(SOUTH SIDE)

NOW HOLD UP
�O��ڶ}���� �o�C�� SHOW NO MERCY
���ڤ@�I�֤l �o�̨S���H��Ǽ�
�@���ɯ�� ż�o�S���H�b�M�z
�A�����w�ڤ]�����w�A �ֺu �S���H�b�G�A
ѡ���ӴN�O���� �@�w�R��
�ѬO�ç��\ �A���Ӥ��q���� ����b �O�h�a
�L���کʩ��a �ٶ�L I WILL SEE YOU OUTSIDE
�O�@�O�@ LIL BITCH SO THE FUCK WHAT

�ڥi�H�Y���]���ȳQ�H���K�y
�U���[���^�� �o���� YOU NEED TO CUT IT
���U�⬰�j �O�̶̦b�����H��A
�פ���� �ڽЧA����ߧA�ۤv �S�X�����ͷN
�Q�~���h���Ѥ�����
�ɾ����諸�n�f�]�Q�H���
���ﹳ�ө��P �ڭ̳��O�K�~
�Q�ѵo�ͤ���� �ڰO�Хu�����q

��08�Ĥ@�Ѩ���
(SOUTH SIDE) (RAIN CITY)
NAH NAH THEY DON��T UNDERSTAND I GOTTA SHOW OUT
(SOUTH SIDE)
��08�Ĥ@�Ѩ���
(SOUTH SIDE) (RAIN CITY)
WUUUUUUU MAN I GOTTA SHOW OUT
(SOUTH SIDE)

�� MOTHER FUCK �� MOTHER FUCK ��
�� MOTHER FUCK �� MOTHER FUCK ��
�� MOTHER FUCK �� MOTHER FUCK ��
�� MOTHER FUCK �� MOTHER FUCK ��

���ަѤl�@���Ʈ� �ٱo�Ƿ|����
�]�U��ڳܪ��s �w���U�d�M
�s�U���ߧC�Y�ݳo�@���ٯ೻��
�ݵۦ۵��ư� �p���ٯ���֬��X�~
�L�̭�}�l�X��?? �۷Q���b����
�u��� �ۤv�h�i ���O�L���ͦ�
�X�Ӯi �n������ �Y�ۧO�ӹL�୮
������y�֧O���� �ݧA��ȴX�⭫
�O�A�Q�ۭɰs�˺� �O���Ѫ���
�H���ǧ� �ڤ��ǤH �̵M�C�ճܵ۰s
�Ĥ��� �ڤ��� �߸����H�q���z
�W�n�񤩴L���|����n�L ���Ӧ@�ڮ�
�S�b�H�D �O��ڬO�f�� �A����
�Q�Q�o�L�Ѥl�ӭn���M�ڤU
���� �U�_�B�r ���W���U
���b�L�n���Ҧb ���٬O�Ѫ���

��08�Ĥ@�Ѩ���
(SOUTH SIDE) (RAIN CITY)
NAH NAH THEY DON��T UNDERSTAND I GOTTA SHOW OUT
(SOUTH SIDE)
��08�Ĥ@�Ѩ���
(SOUTH SIDE) (RAIN CITY)
WUUUUUUU MAN I GOTTA SHOW OUT
(SOUTH SIDE)

����10�~�S���ᮬ�ݤߵL�\
�L���٦b�N��~�~�|�O���٬O����
�p�G�����u�ѤU�R�P�M�����@��
�]���|���H�b�N�A��~�O�_�n�X�d

�ָ˵ħ@�� �A�̸ˣ}�������G��
�ڴ��g�]���A�̣��� �Q�ͪ�����
��o��W�Q���� ���ܱo�S������
�L���ڦY�o�Ӧn ���H�A�̬ݧڨ{�l

�ڱN�s�ڼƦr �����ƻs�K�W
�ֻ��ڧѥ� �ڨ̵M�V�b������W
�C�{���� �ڭ̿�w�b������W
�w��کҦ����Ѷm���_�L�Ӫk�j

���L�@�� �b���|�ݹL�U�بƥ�
�ڪ��D���ǬO�B�� ���ǥu�|��������
�N�O�S���H���ڭ̪��Ӥ��۷n���F
�� �ڭ̬O�B�Ͷ� ���W�O�Q�ڼ���

��08�Ĥ@�Ѩ���

�� MOTHER FUCK �� MOTHER FUCK ��
�� MOTHER FUCK �� MOTHER FUCK ��
�� MOTHER FUCK �� MOTHER FUCK ��
�� MOTHER FUCK �� MOTHER FUCK 

��08�Ĥ@�Ѩ���

�� MOTHER FUCK �� MOTHER FUCK ��
�� MOTHER FUCK �� MOTHER FUCK ��
�� MOTHER FUCK �� MOTHER FUCK ��
�� MOTHER FUCK �� MOTHER FUCK ��

��08�Ĥ@�Ѩ���', to_timestamp('2018/07/10 06:20', 'YYYY/MM/DD HH24:MI'));
commit;