const mysql = require('mysql2');

const db_config = {
	//host: porcess.env.DB_HOST
	//user: porcess.env.DB_USER
	//password: porcess.env.DB_PASS
	//database: porcess.env.MYSQL_DB
    host: "mysql_server",
    user: "dan",
    password: "secret",
    database: "project"
}
// create mysql connection
let dbConn;
let pingInterval = null;

function handleDisconnect(){
    dbConn = mysql.createConnection(db_config);
    
    dbConn.connect(function(error){	
        if(error){
            console.log('error when connecting to db:', err);
			//console.log(`${db_config}`);
            setTimeout(handleDisconnect, 2000);  
        }
        console.log('Database Connected Sucessfully!!!');
    });
    
    dbConn.on('error', function(err) {
        console.log('db error', err);
        if(err.code === 'PROTOCOL_CONNECTION_LOST') {
            
            handleDisconnect();
        } else {
            throw err;
        }
    });
    
    clearInterval(pingInterval);
    pingInterval = setInterval(() => {
        dbConn.ping((err) => {
            if(err) {
                console.log('ping error: ', err);
            }
            console.log ('Ping Sucessfully!!!');
        });
    },3600000*3);
    //return dbConn;
    
}
handleDisconnect();
module.exports = dbConn;
