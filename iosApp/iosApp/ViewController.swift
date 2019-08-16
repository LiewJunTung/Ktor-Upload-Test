import UIKit
import app

class ViewController: UIViewController, FooView {
    
    
    lazy var presenter: FooPresenter = {
        return FooPresenter(fooView: self)
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // label.text = Proxy().proxyHello()
        indicator.startAnimating()
        let url = Bundle.main.url(forResource: "galaxy.png", withExtension: nil)
        print(url!.absoluteString)
        print(FooEnum.bar.weather)
        try!  imageview.image =  UIImage.init(data: Data.init(contentsOf: url!))
       
    }
    var number = 0
    func fooResult(fooCommentList: [Comment]) {
        number = number + 1
        label.text = "\(number): \(fooCommentList.last?.id ?? 0)"
        print("\(fooCommentList.last): \(fooCommentList.last?.id ?? 0)")
    }
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    @IBOutlet weak var label: UILabel!
    @IBAction func button1(_ sender: Any) {
        presenter.loadDb()
    }
    @IBAction func button2(_ sender: Any) {
        var current = 0
        let final = 10
        repeat {
            current = current + 1
            presenter.callNetwork()
           
        } while(current <= final)
        
    }
    
    @IBOutlet weak var indicator: UIActivityIndicatorView!
    @IBOutlet weak var imageview: UIImageView!
}
