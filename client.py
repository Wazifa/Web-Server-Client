
#!/usr/bin/python
import httplib
import  sys
import time

try:
        #initializing the ip address, port number and file name
	ip = sys.argv[1]
	port = sys.argv[2]
	file = sys.argv[3]
	
	#printing out server information to client
	print 'ip = '+ip+' port = '+port+' file name = '+file

	#establish connection
	conn = httplib.HTTPConnection(ip,port)
	conn.request('GET','/'+file)
	res = conn.getresponse()
	
        time
	start = time.time()

	if(res.status != 200):
		print 'Status Code: '+str(res.status)+' '+res.reason
		print 'RTT = ',time.time()-start,'seconds'
		#close connection
		res.close()
		conn.close()

        else:
                print 'Status Code: '+str(res.status) +' '+res.reason
                file = open(file,'w')
                file.write(res.read())
                print 'RTT = ',time.time()-start, 'seconds'

                #closing connection
                res.close()
                conn.close()

except IndexError:
        print 'not enough parms'
except:
        print 'unable to connect'




	
