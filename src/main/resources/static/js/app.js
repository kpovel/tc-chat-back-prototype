let roomId = '';
let token = '';
let userUUID = '';
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

stompClient.onConnect = (frame) => {
    token = $("#token").val();
    roomId = $("#chatUUID").val();
    userUUID = $("#userUUID").val();
    console.log(token + ' token | ' + roomId + ' roomId | ' + userUUID + ' UserUUID');
    const subscriptionAddress = '/topic/' + roomId; // Створіть адресу підписки на основі значення
    setConnected(true);
    stompClient.subscribe(subscriptionAddress, (greeting) => {
        console.log(greeting.body + ' greeting');
        showGreeting(JSON.parse(greeting.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    var message = {
        currentChatUserUUID: userUUID,
        content: $("#content").val(),
        edited: false
    }

    console.log(JSON.stringify(message) + ' message send');

    stompClient.publish({
        destination: '/app/hello/' + roomId,
        body: JSON.stringify(message),
        headers: {
            'authorization': 'Bearer ' + token
        }

    });
}

function showGreeting(message) {
    console.log(message + ' message response')
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});


// let roomId = "f2000b3e-563f-4340-adec-8df0a85a930e";
// const stompClient = new StompJs.Client({
//     brokerURL: 'ws://localhost:8080/ws'
//
// });
//
// stompClient.onConnect = (frame) => {
//
//     roomId = $("#room-id").val(); // Отримайте значення поля room-id з форми
//     const subscriptionAddress = '/topic/' + roomId; // Створіть адресу підписки на основі значення
//     console.log("onContent " + roomId);
//     setConnected(true);
//     console.log('Connected: ' + frame);
//     stompClient.subscribe(subscriptionAddress, (greeting) => {
//         showGreeting(JSON.parse(greeting.body).content);
//     });
// };
//
// stompClient.onWebSocketError = (error) => {
//     console.error('Error with websocket', error);
// };
//
// stompClient.onStompError = (frame) => {
//     console.error('Broker reported error: ' + frame.headers['message']);
//     console.error('Additional details: ' + frame.body);
// };
//
// function setConnected(connected) {
//     $("#connect").prop("disabled", connected);
//     $("#disconnect").prop("disabled", !connected);
//     if (roomId === 'f2000b3e-563f-4340-adec-8df0a85a930e') {
//         if (connected) {
//             $("#conversation").show();
//         } else {
//             // $("#conversation").hide();
//         }
//         // $("#greetings").html("");
//     } else {
//         if (connected) {
//             $("#conversation-2").show();
//         } else {
//             // $("#conversation-2").hide();
//         }
//         // $("#greetings-2").html("");
//
//     }
// }
//
// function connect() {
//     stompClient.activate();
// }
//
//
// function disconnect() {
//     stompClient.deactivate();
//     setConnected(false);
//     console.log("Disconnected");
// }
//
// function sendName() {
//     stompClient.publish({
//         destination: '/app/hello/' + roomId
//         // destination: '/app/hello/' + roomId,
//         // body: JSON.stringify({'content': $("#name").val()}),
//         // headers: { 'authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MUBtYWlsLmNvbSIsImlhdCI6MTY5NzQ3NDgyMiwiZXhwIjoxNjk3NDg0NzIyfQ.wb05bMSHU_cXDgTEuLnqM823LE8ETP-ha6RaCo4e_y4' }
//     });
// }
//
// function showGreeting(message) {
//     console.log("message " + roomId);
//     if (roomId === '1') {
//         $("#greetings").append("<tr><td>" + message + "</td></tr>");
//     } else {
//         $("#greetings-2").append("<tr><td>" + message + "</td></tr>");
//     }
// }
//
// $(function () {
//     $("form").on('submit', (e) => e.preventDefault());
//     $("#connect").click(() => connect());
//     $("#disconnect").click(() => disconnect());
//     $("#send").click(() => sendName());
// });