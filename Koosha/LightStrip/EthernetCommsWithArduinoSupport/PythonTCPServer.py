import socket

HOST = '10.1.0.5'
PORT = 10000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(1)
conn, addr = s.accept()

print "Connected by"
print addr

while 1:
    data = conn.recv(3)
    if not data:
        break
    conn.sendall(data)
conn.close
