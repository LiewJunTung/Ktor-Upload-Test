import UIKit
import app

class ViewController: UIViewController {
    let presenter: FooPresenter = FooPresenter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        label.text = Proxy().proxyHello()

        let url = Bundle.main.url(forResource: "galaxy.png", withExtension: nil)
        print(url!.absoluteString)
        
        try!  imageview.image =  UIImage.init(data: Data.init(contentsOf: url!))
       
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    @IBOutlet weak var label: UILabel!
    @IBAction func button1(_ sender: Any) {
        let url = Bundle.main.url(forResource: "galaxy.png", withExtension: nil)
        let ktByteArray = try! UtilKt.toByteArray(Data.init(contentsOf: url!))
        
        presenter.uploadStuff(uploadUrl: "https://httpbin.org/post", byteArray: ktByteArray, filename: "galaxy.png")
    }
   
    @IBOutlet weak var imageview: UIImageView!
}
