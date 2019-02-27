var config = {
  apiKey: "AIzaSyDsKYJ6RL77F_KmTKVCETGzHGt1jENNG7U",
  authDomain: "trabalho-sd-f1812.firebaseapp.com",
  databaseURL: "https://trabalho-sd-f1812.firebaseio.com",
  projectId: "trabalho-sd-f1812",
  storageBucket: "trabalho-sd-f1812.appspot.com",
  messagingSenderId: "480914949946"
};


firebase.initializeApp(config);
  var db = firebase.database();

  //LOGIN

  firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
      // User is signed in.
  
      document.getElementById("user_div").style.display = "block";
      document.getElementById("login_div").style.display = "none";
  
      var user = firebase.auth().currentUser;
  
      if(user != null){
  
        var email_id = user.email;
        window.location.href="index.html";
        window.alert(document.getElementById("user_para").innerHTML = "Bem vindo : " + email_id);
       
        
      }

      
    } else {
      // No user is signed in.
    
      document.getElementById("user_div").style.display = "none";
      document.getElementById("login_div").style.display = "block";

    }
    
  });
  
  function login(){
  
    var userEmail = document.getElementById("email_field").value;
    var userPass = document.getElementById("password_field").value;
  
    firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) {
      // Handle Errors here.
      var errorCode = error.code;
      var errorMessage = error.message;
  
      window.alert("Erro : " + errorMessage);
  
      // ...
    });
  
  }
  
  function logout(){
    firebase.auth().signOut();
    window.location.href="login.html";
  }
  

    // CREATE REWIEW
  
    var reviewForm = document.getElementById('reviewForm');
    var hiddenId   = document.getElementById('hiddenId');
    var nome       = document.getElementById('nome');
    var autor      = document.getElementById('autor');
    var linguagem  = document.getElementById('linguagem');
    var enderecoGit= document.getElementById('enderecoGit');
    var userGit    = document.getElementById('userGit');
    var senhaGit   = document.getElementById('senhaGit');
    
  
    reviewForm.addEventListener('submit', (e) => {
      e.preventDefault();
    
      if (!nome.value || !autor.value || !linguagem.value || !enderecoGit.value || !userGit.value || !senhaGit.value) return null
    
      var id = hiddenId.value || Date.now()
    
      db.ref('Trabalho/').child(userGit.value).set({
        nome:nome.value,
        autor: autor.value,
        linguagem: linguagem.value,
        enderecoGit: enderecoGit.value,
        userGit: userGit.value,
        senhaGit: senhaGit.value
      });
  
      hiddenId.value = '';
      nome.value = '';
      autor.value = '';
      linguagem.value  = '';
      enderecoGit.value    = '';
      userGit.value  = '';
      senhaGit.value    = '';
      
    });
    
    // READ REVEIWS
    
    var reviews = document.getElementById('reviews');
    var reviewsRef = db.ref('/Trabalho');
    
    reviewsRef.on('child_added', (data) => {
      var li = document.createElement('li')
      li.id = data.key;
      li.innerHTML = reviewTemplate(data.val())
      reviews.appendChild(li);
    });
    
    reviewsRef.on('child_changed', (data) => {
      var reviewNode = document.getElementById(data.key);
      reviewNode.innerHTML = reviewTemplate(data.val());
    });
    
    reviewsRef.on('child_removed', (data) => {
      var reviewNode = document.getElementById(data.key);
      reviewNode.parentNode.removeChild(reviewNode);
    });
    
    reviews.addEventListener('click', (e) => {
      var reviewNode = e.target.parentNode
    
      // UPDATE REVEIW
      if (e.target.classList.contains('edit')) {
      
        nome.value        = reviewNode.querySelector('.nome').innerText;
        autor.value       = reviewNode.querySelector('.autor').innerText;
        linguagem.value   = reviewNode.querySelector('.linguagem').innerText;
        enderecoGit.value = reviewNode.querySelector('.enderecoGit').innerText;
        userGit.value     = reviewNode.querySelector('.userGit').innerText;
        senhaGit.value    = reviewNode.querySelector('.senhaGit').innerText;
        hiddenId.value    = reviewNode.userGit;
      }
    
      // DELETE REVEIW
      if (e.target.classList.contains('delete')) {
        var id = reviewNode.id;
        db.ref('Trabalho/' + id).remove();
      }
    });
    
    function reviewTemplate({nome, autor, linguagem, enderecoGit, userGit, senhaGit}) {
      return `
        <div class='nome'> ${nome}</div>
        <div class='autor'> ${autor}</div>
        <div class='linguagem'> ${linguagem}</div>
        <div class='enderecoGit'> ${enderecoGit}</div>
        <div class='userGit'> ${userGit}</div>
        <div class='senhaGit'>${senhaGit}</div>
        <button class='delete'>Delete</button>
        <button class='edit'>Edit</button>
      `
    };