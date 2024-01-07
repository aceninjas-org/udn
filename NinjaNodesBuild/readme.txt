The jar file is for ACE toolkit
The par file is for ACE Server 

Install par file (plug-in archive file)
1. shutdown ace node
2. create a folder (NOT under ${ACE_INSTALL_ROOT} ), for example C:\ace\UserLIL
3. Copy the par file to this newly created folder
4. open a window command prompt as administrator and run mqsichangeproperties -n lilPath your_node  -v "C:\iib\UserLIL"
5. start the ACE node


Install jar file

1. Close all the ACE toolkits
2. put the jar file under ${ACE_INSTALL-ROOT}/tools/dropins
3. Open a window command prompt as adminstrator and cd to ${ACE_INSTALL_ROOT}
4. Run: ace toolkit -clean -initialize
5. RestartACE Toolkit. Open any message flow and you will be able 
   to see the Ninjas category at the bottom of the pallete of the message flow editor
   
   
   
       