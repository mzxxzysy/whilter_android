var admin = require('firebase-admin')
var serviceAccount = require('./server_key.json')

admin.initializeApp({
	credential : admin.credential.cert(serviceAccount)
})

var token = "cPJMqGsvTHqCJHGpCoFZiM:APA91bHMK5lhgE0pk_DYH9wm7nDdbk0UdzSgF6nRC2xxRkqw79ApraOO1C-tCw1o0i8CvHZ9FnV801bxZiaV-2uhq_7Hx4aC7VVPk3R0vvLSO3M5NmnNmrQ"

var fcm_message = {
/*
	notification: {
		title : 'noti title',
		body : 'noti body..'
	},
*/
	data: {
		title: 'firebase cloud message',
		value: '20'
	},
	token: token
}

admin.messaging().send(fcm_message)
	.then(function(response){
		console.log('send OK...')
	})
	.catch(function(error){
		console.log('send ERROR...')
	})