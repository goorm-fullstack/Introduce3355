package Goorm.Introduce.Domain.FireBase.User;

import Goorm.Introduce.Domain.Member.Member;
import Goorm.Introduce.Domain.User.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 파이어베이스 어드민 기능 구현부
 */
@Service
public class FirebaseUserServiceImpl implements FirebaseUsereService {
    // 입력받은 데이터로 어드민 정보를 생성할 떄 사용
    // 이번 프로젝트에서는 사용하지 않을 예정
    @Override
    public void insertUser(User user) {
        Firestore firestore = FirestoreClient.getFirestore();
        String uid = firestore.collection("user").document().getId();
        ApiFuture<WriteResult> apiFuture = firestore.collection("user").document(uid).set(user);
    }

    // 어드민의 id정보로 데이터베이스에서 어드민을 가져옴
    @Override
    public User getUser(String id) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection("user").document(String.valueOf(id));
        ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
        DocumentSnapshot documentSnapshot = apiFuture.get();

        User user = null;
        if(documentSnapshot.exists()) {
            user = documentSnapshot.toObject(User.class);
            return user;
        } else {
            return null;
        }
    }

    // 어드민 정보를 업데이트하는 경우에 사용
    @Override
    public void updateUser(User user) {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = firestore.collection("user").document(user.getId())
                .set(user);
    }

    // 어드민 계정 삭제
    @Override
    public void deleteUser(String id) {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture = firestore.collection("user")
                .document(id).delete();
    }

    /**
     *
     * @param username @param password
     * @return : 로그인 유저 전달
     */
    @Override
    public User login(String username, String password) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> apiFuture = firestore.collection("user").get();
        List<QueryDocumentSnapshot> documentSnapshots = apiFuture.get().getDocuments();
        User loginUser = null;
        for(QueryDocumentSnapshot snapshot : documentSnapshots) {
            User user = snapshot.toObject(User.class);
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loginUser = user;
                return loginUser;
            }
        }
        return null;
    }
}
