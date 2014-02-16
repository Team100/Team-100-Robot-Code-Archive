import socket

UDP_IP = "10.1.0.64"
UDP_PORT = 8888
MESSAGE = "I am message"

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
