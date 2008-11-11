class ProvincePayment < ActiveRecord::Base
  belongs_to :province
  belongs_to :donor
  
  # Default scope, returns records in default currency
  named_scope :all, :conditions => { 'currency' => Prefs.default_currency }
  named_scope :in_currency, lambda { |cur| { :conditions => { 'currency' => cur} } }
  
  named_scope :published, :conditions => ['data_status = ?', Project::PUBLISHED]
  
  currency_columns :payments, :currency => lambda { |p| p.currency }, :year => lambda { |p| p.year }
end