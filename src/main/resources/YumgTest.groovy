import com.ymg.framework.groovy.model.User

def yumg_test(User user, String abc){
    print(user);
    print(abc);


    def result="""
        [
            {
              "name":$user,
               "bcd":$abc
            }
        ]

    """

    def yumg="nihao adas das"
}

